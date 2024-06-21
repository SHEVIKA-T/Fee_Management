package Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import Model.Admin;
import Model.Mentor;
import Views.ChiefAdminPortal;
import DAO.AdminDAO;
import DAO.MentorDAO;

public class ChiefAdminController {
    private Scanner scanner;
    private AdminDAO adminDAO;
    private MentorDAO mentorDAO;
    private Connection connection;

    public ChiefAdminController() {
        scanner = new Scanner(System.in);
        adminDAO = new AdminDAO();
        mentorDAO = new MentorDAO();
        this.connection = connection;
    }

    public void manageAdmins() {
        System.out.println("Manage Admins");
        System.out.println("1. Add Admin");
        System.out.println("2. Delete Admin");
        System.out.println("3. View Admins");
        System.out.println("0. Go back to main menu");
        System.out.println("Please enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine(); 
        switch (choice) {
            case 1:
                addAdmin();
                manageAdmins();
                break;
            case 2:
                deleteAdmin();
                manageAdmins();
                break;
            case 3:
            	 List<Admin> admins = adminDAO.viewAdmins();
                 for (Admin admin : admins) {
                     System.out.println(admin); 
                 }
                break;
            case 0:
                ChiefAdminPortal.displayMainMenu();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addAdmin() {
        System.out.println("Adding Admin");
        System.out.println("Enter admin ID:");
        String adminId = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        Admin admin = new Admin(adminId, username, password);
        adminDAO.addAdmin(admin);
    }

    private void deleteAdmin() {
        System.out.println("Deleting Admin");
        System.out.println("Enter admin ID to delete:");
        String adminId = scanner.nextLine();
        adminDAO.deleteAdmin(adminId);
    }


    private void viewAdmins() {
        System.out.println("Viewing Admins");
        adminDAO.viewAdmins();
    }
    public void manageMentors() {
        System.out.println("Manage Mentors");
        System.out.println("1. Add Mentor");
        System.out.println("2. Delete Mentor");
        System.out.println("3. View Mentors");
        System.out.println("0. Go back to main menu");
        System.out.println("Please enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine(); 
        switch (choice) {
            case 1:
                addMentor();
                manageMentors();
                break;
            case 2:
                deleteMentor();
                manageMentors();
                break;
            case 3:
                viewMentors();
                break;
            case 0:
                ChiefAdminPortal.displayMainMenu();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addMentor() {
        System.out.println("Adding Mentor");
        System.out.println("Enter mentor ID:");
        String mentorId = scanner.nextLine();
        System.out.println("Enter mentorCourse ID:");
        int mentorCourseId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter mentor name:");
        String mentorName = scanner.nextLine();
        System.out.println("Enter mentor username:");
        String username = scanner.nextLine();
        System.out.println("Enter mentor password:");
        String password = scanner.nextLine();
        Mentor mentor = new Mentor(mentorId,mentorCourseId, mentorName, username, password);
        mentorDAO.addMentor(mentor);
    }

    private void deleteMentor() {
        System.out.println("Deleting Mentor");
        System.out.println("Enter mentor ID to delete:");
        String mentorId = scanner.nextLine();
        mentorDAO.deleteMentor(mentorId);
    }

    private void viewMentors() {
        System.out.println("Viewing Mentors");
        List<Mentor> mentors = mentorDAO.viewMentors();
        for (Mentor mentor : mentors) {
            System.out.println(mentor); 
        }
    }
    public void updateCourseDetails() {
        try {
            System.out.println("Enter the course ID:");
            String courseId = scanner.nextLine();

            System.out.println("Enter the new course fee:");
            int courseFee = scanner.nextInt();
            scanner.nextLine(); 

            
            System.out.println("Enter the new due date (YYYY-MM-DD):");
            String dueDate = scanner.nextLine();
            
            String query = "UPDATE Course SET course_fee = ?, due_date = ? WHERE course_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseFee);
            statement.setString(2, dueDate);
            statement.setString(3, courseId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Course details updated successfully.");
            } else {
                System.out.println("Failed to update course details. Please check the course ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating course details: " + e.getMessage());
        }
    }

}

