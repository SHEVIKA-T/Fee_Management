package Views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Model.DBConnection;
import Controller.MentorController;
import Views.FeePortal;
    public class MentorPortal {
        private Scanner scanner;
        private Connection connection;
        private MentorController mentorController;
        private FeePortal feeportal;
		private String username;
        public MentorPortal() {
            this.scanner = new Scanner(System.in);
            this.connection = DBConnection.getConnection();
            this.mentorController = new MentorController();
            this.feeportal = new FeePortal();
        }

    public static void displayMainMenu() {
        System.out.println("Welcome to Mentor Portal");
        System.out.println("1. Manage Students");
        System.out.println("2. Update Password");
        System.out.println("3. FeePortalLogin");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    public void handleMainMenuInput() {
        if (authenticateMentor()) {
            System.out.println("Authentication successful. You have access to Mentor Portal.");
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                	mentorController.manageStudents();
                    break;
                case 2:
                	System.out.println("Enter username");
                	String username=scanner.next();
                	System.out.println("Enter new password");
                	String newpassword=scanner.next();
                	mentorController.updatePassword(newpassword,username);
                    break;
                case 3:
                	feeportal.displayMainMenu();
                    feeportal.handleMainMenuInput();
                    break;
                case 0:
                    System.out.println("Exiting Mentor Portal.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            
        } else {
            System.out.println("Invalid credentials. Exiting Mentor Portal.");
            feeportal.displayMainMenu();
            feeportal.handleMainMenuInput();
        }
    }
    private boolean authenticateMentor() {
        System.out.println("Please enter your mentor_courseid:");
        String mentorCourseId = scanner.nextLine();
        System.out.println("Please enter your username:");
        this.username = scanner.nextLine();
        mentorController.setUsername(username);
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        try {
            String query = "SELECT * FROM mentors WHERE mentor_courseid = ? AND username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mentorCourseId);
            statement.setString(2, this.username); 
            statement.setString(3, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
}
