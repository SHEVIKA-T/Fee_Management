//package Views;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.Scanner;
//
//import Controller.StudentController;
//import Model.DBConnection;
//
//public class StudentPortal {
//    private Scanner scanner;
//    private Connection connection;
//    private StudentController studentController;
//
//    public StudentPortal() {
//        this.scanner = new Scanner(System.in);
//        this.connection = DBConnection.getConnection();
//        this.studentController=new StudentController();
//    }
//
//    public void displayMainMenu() {
//        System.out.println("Welcome to Student Portal");
//        System.out.println("1. My Current Year Fee Status");
//        System.out.println("2. My Pre Fee Status");
//        System.out.println("3. Update Password");
//        System.out.println("0. Exit");
//        System.out.println("Please enter your choice:");
//    }
//
//    public void handleMainMenuInput() throws SQLException {
////        System.out.println("Please enter your ID:");
////        String id = scanner.nextLine();
//
//        // Placeholder for authentication logic
//        if (authenticateStudent()) {
//            System.out.println("Authentication successful.");
//
//            boolean exit = false;
//            while (!exit) {
//                displayMainMenu();
//                int choice = scanner.nextInt();
//                scanner.nextLine(); // Consume newline
//
//                switch (choice) {
//                    case 1:
//                        studentController.MyPreFeeReport(id);
//                        break;
//                    case 2:
//                    	studentController.MyCurrentYearFeeReport(id);
//                        break;
//                    case 3:
//                    	studentController.updatePassword(id);
//                        break;
//                    case 0:
//                        System.out.println("Exiting Student Portal.");
//                        exit = true;
//                        break;
//                    default:
//                        System.out.println("Invalid choice.");
//                }
//            }
//        } else {
//            System.out.println("Authentication failed. Exiting Student Portal.");
//        }
//    }
//
//    private boolean authenticateStudent() {
//        System.out.println("Please enter your student ID:");
//        String studentId = scanner.nextLine();
//        System.out.println("Please enter your password:");
//        String password = scanner.nextLine();
//
//        // Query the database or authenticate against any other mechanism for student authentication
//        try {
//            String query = "SELECT * FROM students WHERE student_id = ? AND password = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, studentId);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.next(); // If any row is returned, authentication successful
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error appropriately
//            return false;
//        }
//    }
//
//
//   
//}
package Views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.StudentController;
import Model.DBConnection;

public class StudentPortal {
    private Scanner scanner;
    private Connection connection;
    private StudentController studentController;

    public StudentPortal() {
        this.scanner = new Scanner(System.in);
        this.connection = DBConnection.getConnection();
        this.studentController = new StudentController();
    }

    public void displayMainMenu() {
        System.out.println("Welcome to Student Portal");
        System.out.println("1. My Current Year Fee Status");
        System.out.println("2. My Pre Fee Status");
        System.out.println("3. My Payment History");
        System.out.println("4. Update Password");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    public void handleMainMenuInput() {
        System.out.println("Please enter your student ID:");
        String id = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        if (authenticateStudent(id, password)) {
            System.out.println("Authentication successful.");

            boolean exit = false;
            while (!exit) {
                displayMainMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
					try {
						studentController.MyCurrentYearFeeReport(id);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
                        break;
                    case 2:
					try {
						studentController.MyPreFeeReport(id);
					} catch (SQLException e) {
						e.printStackTrace();
					}
                        break;
                    case 3:
                    	studentController.PaymentHistory(id);
                        break;
                    	
                    case 4:
                        studentController.updatePassword(id);
                        break;
                    case 0:
                        System.out.println("Exiting Student Portal.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting Student Portal.");
        }
    }

    private boolean authenticateStudent(String studentId, String password) {
        try {
            String query = "SELECT * FROM students WHERE stu_id = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, studentId);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
}

