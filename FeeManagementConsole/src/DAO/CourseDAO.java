package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Course;
import Model.DBConnection;

public class CourseDAO {

    public int getCourseFeeByCourseId(int courseId) {
        int courseFee = 0;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT course_fee FROM course WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                courseFee = resultSet.getInt("course_fee");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return courseFee;
    }
    
}

