package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import DAO.StudentDAO;
import DAO.CourseDAO;
import DAO.PaymentDAO;
import DAO.PreFeeReportDAO;
import Model.Course;
import Model.DBConnection;
import Model.Mentor;
import Model.Payment;
import Model.PreFeeReport;
import Model.Student; 
import Views.MentorPortal;
import Views.FeePortal;
public class MentorController {
    private Scanner scanner;
    private StudentDAO studentDAO;
    private Connection connection;
    private FeePortal feeportal;
	private String username;
	private PaymentDAO paymentDAO;
    private PreFeeReportDAO preFeeReportDAO;
    
    public MentorController() {
        this.scanner = new Scanner(System.in);
        this.studentDAO = new StudentDAO();
        this.connection = DBConnection.getConnection();
        this.feeportal = new FeePortal();
       
       
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void manageStudents() {
        System.out.println("Manage Students");
        //System.out.println("1. Update CourseYear");
        System.out.println("1. Add Students");
        System.out.println("2. Update Students");
        System.out.println("3. Delete Students");
        System.out.println("4. View Students");
        System.out.println("0. Go back to main menu");
        System.out.println("Please enter your choice:");

        int choice = scanner.nextInt();
        switch (choice) {
//        	case 1:
//        		
//        		System.out.println("Enter newCourseYear:");
//        		int newCourseYear=scanner.nextInt();
//        		//Mentor mentor = new Mentor();
//        		//int mentorCourseId = mentor.getMentorCourseId();
//        		updateCourseYear(newCourseYear);
//        		break;
            case 1:
                addStudents();
                manageStudents();
                break;
            case 2:
                System.out.println("Which fields do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Course ID");
                System.out.println("3. CourseYear");
                System.out.println("4. email");
                System.out.println("5. phoneNumber");
                System.out.println("6. dateOfBirth");
                
                int innerChoice = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (innerChoice) {
                    case 1:
                        System.out.println("Enter student ID:");
                        String studentIdName = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new name:");
                        String newName = scanner.nextLine();
                        studentDAO.updateStudent(studentIdName, "name", newName);
                        break;
                    case 2:
                        System.out.println("Enter student ID:");
                        String studentIdCourseId = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new course ID:");
                        String newCourseId = scanner.nextLine();
                        studentDAO.updateStudent(studentIdCourseId, "courseId", newCourseId);
                        break;
                    case 3:
                        System.out.println("Enter student ID:");
                        String studentIdYear = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new year:");
                        int newYear = scanner.nextInt();
                        studentDAO.updateStudent(studentIdYear, "year", String.valueOf(newYear));
                        break;
                    case 4:
                        System.out.println("Enter student ID:");
                        String studentIdEmail = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new email:");
                        String newEmail = scanner.nextLine();
                        studentDAO.updateStudent(studentIdEmail, "email", newEmail);
                        break;
                    case 5:
                        System.out.println("Enter student ID:");
                        String studentIdPhoneNumber = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new phone number:");
                        String newPhoneNumber = scanner.nextLine();
                        studentDAO.updateStudent(studentIdPhoneNumber, "phoneNumber", newPhoneNumber);
                        break;
                    case 6:
                        System.out.println("Enter student ID:");
                        String studentIdDateOfBirth = scanner.next();
                        scanner.nextLine(); 
                        System.out.println("Enter new date of birth:");
                        String newDateOfBirth = scanner.nextLine();
                        studentDAO.updateStudent(studentIdDateOfBirth, "dateOfBirth", newDateOfBirth);
                        break;
                    default:
                        System.out.println("Invalid inner choice");
                }
                break;

            case 3:
            	System.out.println("Enter student id :");
            	String stuid=scanner.next();
            	boolean deleted = studentDAO.deleteStudent(stuid);
                if (deleted) {
                    System.out.println("Students deleted successfully.");
                    manageStudents();
                } else {
                    System.out.println("Failed to delete students.");
                    manageStudents();
                }
                break;
            case 4:

                int mentorCourseId = retrieveMentorCourseId(this.username); 
                List<Student> students = studentDAO.viewStudents(mentorCourseId);
                for (Student student : students) {
                    System.out.println(student); 
                }
                manageStudents();
                break;
            case 0:
            	MentorPortal.displayMainMenu();
                int choice1 = scanner.nextInt();
                switch (choice1) {
                    case 1:
                    	manageStudents();
                        break;
                    case 2:
                    	System.out.println("Enter username");
                    	String username1=scanner.next();
                    	System.out.println("Enter new password");
                    	String newpassword=scanner.next();
                    	updatePassword(newpassword,username1);
                    	break;
                    case 0:
                        System.out.println("Exiting Mentor Portal.");
                       
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    public int retrieveMentorCourseId(String username) {
        int mentorCourseId = -1; 

        try {
            String query = "SELECT mentor_courseid FROM mentors WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mentorCourseId = resultSet.getInt("mentor_courseid");
            } else {
                System.out.println("Mentor not found for username: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return mentorCourseId;
    }
    public void updateCourseYear(int newCourseYear) {
        try {
            List<Payment> payments = paymentDAO.getAllPayments();

           
            for (Payment payment : payments) {
                PreFeeReport preFeeReport = new PreFeeReport(payment);
                preFeeReportDAO.addPreFeeReport(preFeeReport);
            }

            paymentDAO.updateAllPaymentsCourseYear(newCourseYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addStudents() {
        System.out.println("Add Student");
        System.out.println("Enter student ID:");
        String stu_id = scanner.next();
        scanner.nextLine(); 

        System.out.println("Enter student name:");
        String stu_name = scanner.nextLine();

        System.out.println("Enter course ID:");
        int course_id = scanner.nextInt();

        System.out.println("Enter course year:");
        int course_year = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Enter student email:");
        String stu_mail = scanner.nextLine();

        System.out.println("Enter student phone number:");
        String stu_Phno = scanner.nextLine();

        System.out.println("Enter student date of birth:");
        String stu_dob = scanner.nextLine();

        System.out.println("Enter student password:");
        String password = scanner.nextLine();
        Student newStudent = new Student(stu_id, stu_name, course_id, course_year, stu_mail, stu_Phno, stu_dob, password);

       
        StudentDAO studentDAO = new StudentDAO();
        studentDAO.addStudent(newStudent);
    }

    public void updatePassword(String newPassword, String username) {
        
        try {
            String query = "UPDATE mentors SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully.");
                feeportal.displayMainMenu();
                feeportal.handleMainMenuInput();
            } else {
                System.out.println("Failed to update password. User not found.");
                feeportal.displayMainMenu();
                feeportal.handleMainMenuInput();
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }


   
}
