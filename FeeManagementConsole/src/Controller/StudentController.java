package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Model.DBConnection;

public class StudentController {
    private Scanner scanner;
    private Connection connection;

    public StudentController() {
        this.scanner = new Scanner(System.in);
        this.connection = DBConnection.getConnection();
    }

    public void MyPreFeeReport(String id) throws SQLException {
        String query = "SELECT * FROM PreFeeReport WHERE stu_id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, id);
            printResultSet(statement);
        }
    }
    public void MyCurrentYearFeeReport(String id) throws SQLException {
    	String query = "SELECT * FROM payment where stu_id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, id);
            printResultSet(statement);
        }
    }


    public void printResultSet(PreparedStatement statement) throws SQLException {
        try (ResultSet resultSet = statement.executeQuery()) {
            // Print the header
        	System.out.println(" --|MY REPORT|--");
            System.out.println("+--------+----------------+-----------+-------------+-----------+------------+-------------+---------------------------+");
            System.out.println("| stu_id | lastpaidfee_id | course_id | course_year | Total_fee | fee_status | fee_balance | comments                  |");
            System.out.println("+--------+----------------+-----------+-------------+-----------+------------+-------------+---------------------------+");
            
          
            while (resultSet.next()) {
                String stu_id = resultSet.getString("stu_id");
                String lastpaidfee_id = resultSet.getString("lastpaidfee_id");
                int course_id = resultSet.getInt("course_id");
                int course_year = resultSet.getInt("course_year");
                int Total_fee = resultSet.getInt("Total_fee");
                String fee_status = resultSet.getString("fee_status");
                int fee_balance = resultSet.getInt("fee_balance");
                String comments = resultSet.getString("comments");
                System.out.printf("| %-6s | %-14s | %-9s | %-11s | %-9s | %-10s | %-11s | %-25s |\n",
                        stu_id, lastpaidfee_id, course_id, course_year, Total_fee, fee_status, fee_balance, comments);
            }
        
            System.out.println("+--------+----------------+-----------+-------------+-----------+------------+-------------+---------------------------+");
        }
    }


public void PaymentHistory(String stu_id) {
    String query = "SELECT * FROM payment_history WHERE stu_id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, stu_id);
        try (ResultSet resultSet = statement.executeQuery()) {
         
        	System.out.println(" --|MY REPORT|--");
            System.out.println("+-----------+-------------+-----------+-------------+------------+");
            System.out.println("| Payment ID| Course Year | Total Fee | Amount Paid | Payment Date|");
            System.out.println("+-----------+-------------+-----------+-------------+------------+");
            while (resultSet.next()) {
                String payment_id = resultSet.getString("fee_id");
                int course_year = resultSet.getInt("course_year");
                int total_fee = resultSet.getInt("total_fee");
                int amount_paid = resultSet.getInt("paid_amount");
                String payment_date = resultSet.getString("paid_date");

                
                System.out.printf("| %-10s| %-12s| %-10s| %-12s| %-12s|\n", payment_id, course_year, total_fee, amount_paid, payment_date);
            }
            System.out.println("+-----------+-------------+-----------+-------------+------------+");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public void updatePassword(String studentId) {
    try {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your new password:");
        String newPassword = scanner.nextLine();

        String query = "UPDATE students SET password = ? WHERE stu_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, newPassword);
        statement.setString(2, studentId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Password updated successfully");
        } else {
            System.out.println("Failed to update password");
        }
    } catch (SQLException e) {
        System.out.println("Error updating password: " + e.getMessage());
    }
}

}