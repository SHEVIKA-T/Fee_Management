package Controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Model.DBConnection;
import DAO.PaymentDAO;
import Views.MentorPortal;

public class AdminController {
    private Scanner scanner;
    private Connection connection;
    
    public AdminController() {
        this.scanner = new Scanner(System.in);
        this.connection = DBConnection.getConnection();
    }

    public void updatePayment() {
        System.out.println("Enter Student ID:");
        String stuId = scanner.nextLine();
        System.out.println("Enter Fee ID:");
        String feeId = scanner.next();
        System.out.println("Enter Course Year:");
        int courseYear = scanner.nextInt();
        System.out.println("Enter Paid Amount:");
        int paidAmount = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Enter Paid Date (YYYY-MM-DD):");
        String paidDate = scanner.nextLine();

        try {
        	 int totalFee = getTotalFee(stuId);
            updateFeeBalance(stuId, paidAmount,paidDate);
            updateLastPaidFeeId(stuId, feeId);
            String query = "INSERT INTO payment_history (stu_id, fee_id, course_year,total_fee,paid_amount, paid_date) VALUES (?, ?, ?,?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stuId);
            statement.setString(2, feeId);
            statement.setInt(3, courseYear);
            statement.setInt(4, totalFee);
            statement.setInt(5, paidAmount);
            statement.setString(6, paidDate);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment history record inserted successfully.");
                
            } else {
                System.out.println("Failed to insert payment history record.");
                
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    private void updateFeeBalance(String stuId, int paidAmount,String paidDate) throws SQLException {
       
        String query = "SELECT fee_balance FROM payment WHERE stu_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, stuId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int currentBalance = resultSet.getInt("fee_balance");
            int newBalance = currentBalance - paidAmount;
            if (newBalance < 0) {
                newBalance = 0;
            }
            query = "UPDATE payment SET fee_balance = ? WHERE stu_id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, newBalance);
            statement.setString(2, stuId);
            statement.executeUpdate();
            updatePaymentComments(stuId, paidDate,newBalance);
            if (newBalance <= 0) {
                query = "UPDATE payment SET fee_status = 'paid' WHERE stu_id = ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, stuId);
                statement.executeUpdate();
            }
        }
    }

    private void updateLastPaidFeeId(String stuId, String feeId) throws SQLException {
        String query = "UPDATE payment SET lastpaidfee_id = ? WHERE stu_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, feeId);
        statement.setString(2, stuId);
        statement.executeUpdate();
    }
    public void updatePaymentComments(String stuId, String paidDate, int newBalance) {
        try {
            String dueDateQuery = "SELECT due_date FROM course WHERE course_id = (SELECT course_id FROM students WHERE stu_id = ?)";
            PreparedStatement dueDateStatement = connection.prepareStatement(dueDateQuery);
            dueDateStatement.setString(1, stuId);
            ResultSet dueDateResult = dueDateStatement.executeQuery();
            
            if (dueDateResult.next()) {
                Date dueDate = dueDateResult.getDate("due_date");
                Date paymentDate = Date.valueOf(paidDate);
                String comments = "";
                if (paymentDate.compareTo(dueDate) <= 0) {
                    if (newBalance > 0) {
                        comments = "within due and balance remaining";
                    } else {
                        comments = "within due and no balance";
                    }
                } else {
                    if (newBalance > 0) {
                        comments = "overdue and balance outstanding";
                    } else {
                        comments = "overdue but cleared";
                    }
                }
                String updateQuery = "UPDATE payment SET comments = ? WHERE stu_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, comments);
                updateStatement.setString(2, stuId);
                updateStatement.executeUpdate();
            } else {
                System.out.println("Due date not found for the course.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    private int getTotalFee(String stuId) throws SQLException {
        int totalFee = 0;
        String query = "SELECT course_fee FROM course WHERE course_id = (SELECT course_id FROM students WHERE stu_id = ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, stuId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            totalFee = resultSet.getInt("course_fee");
        }
        return totalFee;
    }


    public int getCourseIdByStudentId(String stuId) {
        int courseId = -1;
        try {
            String query = "SELECT course_id FROM students WHERE stu_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stuId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                courseId = resultSet.getInt("course_id");
            } else {
                System.out.println("Student ID not found: " + stuId);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return courseId;
    }

    public void viewAllFeeReports() {
        try {
            String query = "SELECT * FROM payment";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

         
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String lastPaidFeeId = resultSet.getString("lastpaidfee_id");
                int courseId = resultSet.getInt("course_id");
                int courseYear = resultSet.getInt("course_year");
                int courseFee=resultSet.getInt("Total_fee");
                String feeStatus = resultSet.getString("fee_status");
                int feeBalance = resultSet.getInt("fee_balance");
                String comments = resultSet.getString("comments");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------ ");
                System.out.println("Student ID: " + stuId + ", Last Paid Fee ID: " + lastPaidFeeId +
                                   ", Course ID: " + courseId + ", Course Year: " + courseYear +", Total fee: " + courseFee+ 
                                   ", Fee Status: " + feeStatus + ", Fee Balance: " + feeBalance +
                                   ", Comments: " + comments);
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving fee reports:");
            e.printStackTrace();
        }
    }
    public void viewAllPaymentHistories() {
        try {
            String query = "SELECT * FROM payment_history";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Payment Histories for payment_history table:");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeId = resultSet.getString("fee_id");
                int courseYear = resultSet.getInt("course_year");
                int totalFee = resultSet.getInt("total_fee");
                int paidAmount = resultSet.getInt("paid_amount");
                String paidDate = resultSet.getString("paid_date");
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------ ");
                System.out.println("Student ID: " + stuId + ", Fee ID: " + feeId +
                                   ", Course Year: " + courseYear + ", Total Fee: " + totalFee + 
                                   ", Paid Amount: " + paidAmount + ", Paid Date: " + paidDate);
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------ ");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving payment histories for payment_history table:");
            e.printStackTrace();
        }
    }
    public void viewStudentPaymentHistory(String studentId) {
        try {
            String query = "SELECT * FROM payment_history WHERE stu_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Payment History for Student ID: " + studentId);
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeId = resultSet.getString("fee_id");
                int courseYear = resultSet.getInt("course_year");
                int totalFee = resultSet.getInt("total_fee");
                int paidAmount = resultSet.getInt("paid_amount");
                String paidDate = resultSet.getString("paid_date");
                
                
                String paymentDetails = "Student ID: " + stuId + ", Fee ID: " + feeId +
                                        ", Course Year: " + courseYear + ", Total Fee: " + totalFee +
                                        ", Paid Amount: " + paidAmount + ", Paid Date: " + paidDate;
              
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------ ");
                System.out.println(paymentDetails);
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------ ");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while retrieving payment history for student ID: " + studentId);
            e.printStackTrace();
        }
    }

    public void updatePassword() {
        System.out.println("Enter admin ID:");
        String adminId = scanner.nextLine();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        try {
            String query = "UPDATE admin SET password = ? WHERE admin_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, adminId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Failed to update password. Admin ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
        }
    }


}   



   
