//package DAO;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//import Model.DBConnection;
//import Model.Payment;
//
//
//
//public class PaymentDAO {
//	private Connection connection;
//
//    public PaymentDAO() {
//        // Obtain the database connection from the DBConnection class
//        this.connection = DBConnection.getConnection();
//    }
//    public void addPayment(Payment payment) {
//        try {
//            String insertPaymentQuery = "INSERT INTO payment (stu_id, lastpaidfee_id, course_id, course_year, fee_status, fee_balance, comments) VALUES (?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(insertPaymentQuery);
//
//            statement.setString(1, payment.getStuId());
//            statement.setInt(2, payment.getLastPaidFeeId());
//            statement.setInt(3, payment.getCourseId());
//            statement.setInt(4, payment.getCourseYear());
//            statement.setString(5, payment.getFeeStatus());
//            statement.setInt(6, payment.getFeeBalance());
//            statement.setString(7, payment.getComments());
//
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//    }
//}

//package DAO;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import Model.DBConnection;
//import Model.Payment;
//
//public class PaymentDAO {
//    public void addPayment(Payment payment) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        
//        try {
//            connection = DBConnection.getConnection();
//            String insertPaymentQuery = "INSERT INTO payment (stu_id, lastpaidfee_id, course_id, course_year, fee_status, fee_balance, comments) VALUES (?, ?, ?, ?, ?, ?, ?)";
//            statement = connection.prepareStatement(insertPaymentQuery);
//
//            statement.setString(1, payment.getStuId());
//            statement.setInt(2, payment.getLastPaidFeeId());
//            statement.setInt(3, payment.getCourseId());
//            statement.setInt(4, payment.getCourseYear());
//            statement.setString(5, payment.getFeeStatus());
//            statement.setInt(6, payment.getFeeBalance());
//            statement.setString(7, payment.getComments());
//
//            statement.executeUpdate();
//            System.out.println("Payment added successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace(); // Handle error closing statement or connection
//            }
//        }
//    }
//}
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.DBConnection;
import Model.Payment;
import Model.PreFeeReport;

public class PaymentDAO {
    private Connection connection;
	//private PaymentDAO paymentDAO;
	private PreFeeReportDAO preFeeReportDAO;
    

    public PaymentDAO() {
        this.connection = DBConnection.getConnection();
     //   this.paymentDAO = new PaymentDAO();
        this.preFeeReportDAO = new PreFeeReportDAO();
    }
  

    public void addPayment(Payment payment) {
        try {
            String insertPaymentQuery = "INSERT INTO payment (stu_id, lastpaidfee_id, course_id, course_year,Total_fee, fee_status, fee_balance, comments) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(insertPaymentQuery);

            statement.setString(1, payment.getStuId());
            statement.setString(2, payment.getLastPaidFeeId());
            statement.setInt(3, payment.getCourseId());
            statement.setInt(4, payment.getCourseYear());
            statement.setInt(5, payment.getTotalFee());
            statement.setString(6, payment.getFeeStatus());
            statement.setInt(7, payment.getFeeBalance());
            statement.setString(8, payment.getComments());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM payment";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String lastPaidFeeId = resultSet.getString("lastpaidfee_id");
                int courseId = resultSet.getInt("course_id");
                int courseYear = resultSet.getInt("course_year");
                int totalFee = resultSet.getInt("total_fee");
                String feeStatus = resultSet.getString("fee_status");
                int feeBalance = resultSet.getInt("fee_balance");
                String comments = resultSet.getString("comments");
                Payment payment = new Payment(stuId, lastPaidFeeId, courseId, courseYear, totalFee, feeStatus, feeBalance, comments);

                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public void updateAllPaymentsCourseYear(int newCourseYear) {
        String query = "UPDATE payment SET course_year=?, fee_balance = fee_balance + total_fee, comments = 'nil'";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newCourseYear);
            int rowsUpdated = statement.executeUpdate();
            System.out.println(rowsUpdated + " rows updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateCourseYear(int newCourseYear) {
        try {
            List<Payment> payments = getAllPayments();

           
            for (Payment payment : payments) {
                PreFeeReport preFeeReport = new PreFeeReport(payment);
                preFeeReportDAO.addPreFeeReport(preFeeReport);
            }

            updateAllPaymentsCourseYear(newCourseYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}

