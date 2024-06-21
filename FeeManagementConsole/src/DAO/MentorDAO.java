package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Mentor;
import Model.DBConnection;

public class MentorDAO {
    private Connection connection;
    

    public MentorDAO() {
        this.connection = DBConnection.getConnection();
    }

    public void addMentor(Mentor mentor) {
        try {
            String query = "INSERT INTO mentors (mentor_id,mentor_courseid,mentor_name, username, password) VALUES (?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mentor.getMentorId());
            statement.setInt(2, mentor.getMentorCourseId());
            statement.setString(3, mentor.getMentorName());
            statement.setString(4, mentor.getUsername());
            statement.setString(5, mentor.getPassword());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Mentor added successfully.");
            } else {
                System.out.println("Failed to add mentor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMentor(String mentorId) {
        try {
            String query = "DELETE FROM mentors WHERE mentor_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mentorId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Mentor deleted successfully.");
            } else {
                System.out.println("Failed to delete mentor.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mentor> viewMentors() {
        List<Mentor> mentors = new ArrayList<>();
        try {
            String query = "SELECT  mentor_id,mentor_courseid, mentor_name, username FROM mentors";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String mentorId = resultSet.getString("mentor_id");
                int mentorCourseId=resultSet.getInt("mentor_courseid");
                String mentorName = resultSet.getString("mentor_name");
                String username = resultSet.getString("username");
                Mentor mentor = new Mentor(mentorId,mentorCourseId, mentorName,username);
                mentors.add(mentor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentors;
    }
}
