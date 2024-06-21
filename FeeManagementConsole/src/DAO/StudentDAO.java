package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Controller.MentorController;
import Model.Student;
import Model.DBConnection; // Import the DBConnection class
import Model.Payment;

public class StudentDAO {
    private Connection connection;
   // private MentorController mentorController;
    public StudentDAO() {
        this.connection = DBConnection.getConnection();
       // this.mentorController = new MentorController();
    }

    public void addStudent(Student student) {
        try {
            String query = "INSERT INTO students (stu_id,stu_name, course_id, course_year, stu_mail, stu_Phno, stu_dob,password) VALUES (?, ?, ?, ?, ?, ?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, student.getStu_id());
            statement.setString(2, student.getStu_name());
            statement.setInt(3, student.getCourse_id());
            statement.setInt(4, student.getCourse_year());
            statement.setString(5, student.getStu_mail());
            statement.setString(6, student.getStu_Phno());
            statement.setString(7, student.getStu_dob());
            statement.setString(8, student.getPassword());
            statement.executeUpdate();
            addPaymentForNewStudent(student);
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void addPaymentForNewStudent(Student student) {
        try {
        	int courseId = student.getCourse_id();
            int courseYear = student.getCourse_year();
            int courseFee = getCourseFee(courseId);
           
            
            Payment payment = new Payment();
            payment.setStuId(student.getStu_id());
            payment.setLastPaidFeeId("nil"); 
            payment.setCourseId(student.getCourse_id());
            payment.setCourseYear(student.getCourse_year());
            payment.setTotalFee(courseFee);
            payment.setFeeStatus("pending");
            payment.setInitialFeeBalance(courseFee); 
            payment.setComments("Nill");
            PaymentDAO paymentDAO = new PaymentDAO();
            paymentDAO.addPayment(payment);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    private int getCourseFee(int courseId) {
        int courseFee = 0;
        try {
            String query = "SELECT course_fee FROM course WHERE course_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, courseId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        courseFee = resultSet.getInt("course_fee");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return courseFee;
    }
    public boolean deleteStudent(String studentId) {
        try {
            String query = "DELETE FROM students WHERE stu_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0; 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false; 
        }
    }
    public  void updateStudent(String studentId, String fieldToUpdate, String newValue) {
        String sql = "UPDATE students SET ";
        
        switch (fieldToUpdate) {
            case "name":
                sql += "stu_name = ?";
                break;
            case "courseId":
                sql += "course_id = ?";
                break;
            case "year":
                sql += "course_year = ?";
                break;
            case "email":
                sql += "stu_mail = ?";
                break;
            case "phoneNumber":
                sql += "stu_Phno = ?";
                break;
            case "dateOfBirth":
                sql += "stu_dob = ?";
                break;
            default:
                System.err.println("Invalid field to update");
                return;
        }
        
        sql += " WHERE stu_id = ?";
        
        try {
             PreparedStatement statement = connection.prepareStatement(sql) ;
            statement.setString(1, newValue);
            statement.setString(2, studentId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student information updated successfully.");
                MentorController mentorController = new MentorController(); 
                mentorController.manageStudents(); 
            } else {
                System.out.println("Failed to update student information.");
               // mentorController.manageStudents();
            }
        } catch (SQLException e) {
            System.err.println("Error updating student information: " + e.getMessage());
        }
    }


    public List<Student> viewStudents(int mentorCourseId) {
        List<Student> students = new ArrayList<>();
        try {
            String query = "SELECT * FROM students where course_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, mentorCourseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String stuName = resultSet.getString("stu_name");
                int courseId = resultSet.getInt("course_id");
                int courseYear = resultSet.getInt("course_year");
                String stuMail = resultSet.getString("stu_mail");
                String stuPhno = resultSet.getString("stu_Phno");
                String stuDob = resultSet.getString("stu_dob");
                String password = resultSet.getString("password");

                Student student = new Student(stuId, stuName, courseId, courseYear, stuMail, stuPhno, stuDob, password);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return students;
    }
    public boolean updateCourseYear( int newCourseYear) {
        try {
            String query = "UPDATE students SET course_year = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newCourseYear);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}

