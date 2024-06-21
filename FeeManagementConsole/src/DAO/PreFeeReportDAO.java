package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.DBConnection;
import Model.PreFeeReport;

public class PreFeeReportDAO {
    private Connection connection;

    public PreFeeReportDAO() {
        connection = DBConnection.getConnection();  
    }

    public void addPreFeeReport(PreFeeReport preFeeReport) {
        try {
            String query = "INSERT INTO PreFeeReport (stu_id, lastpaidfee_id, course_id, course_year, Total_fee, fee_status, fee_balance, comments) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, preFeeReport.getStuId());
            statement.setString(2, preFeeReport.getLastPaidFeeId());
            statement.setInt(3, preFeeReport.getCourseId());
            statement.setInt(4, preFeeReport.getCourseYear());
            statement.setInt(5, preFeeReport.getTotalFee());
            statement.setString(6, preFeeReport.getFeeStatus());
            statement.setInt(7, preFeeReport.getFeeBalance());
            statement.setString(8, preFeeReport.getComments());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

