package Views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Controller.ChiefAdminController;
import Controller.GenerateDetailedReport;
import Controller.GeneratePaidReport;
import Controller.GeneratePendingReport;
import DAO.PaymentDAO;
import DAO.StudentDAO;
import Model.DBConnection;


public class ChiefAdminPortal {
    private Scanner scanner; 
    private Connection connection;
    private ChiefAdminController chiefadminController;
    private GeneratePaidReport generatePaidReport;
    private GeneratePendingReport generatePendingReport;
    private GenerateDetailedReport generateDetailedReport;
	private PaymentDAO paymentDAO;
	private StudentDAO studentDAO;

    public ChiefAdminPortal() {
        this.scanner = new Scanner(System.in);
        this.connection = DBConnection.getConnection();
        this.chiefadminController = new ChiefAdminController();
        this.paymentDAO = new PaymentDAO();
        this.studentDAO = new StudentDAO();
        this.generatePaidReport = new GeneratePaidReport(connection);
        this.generatePendingReport = new GeneratePendingReport(connection);
       this. generateDetailedReport=new GenerateDetailedReport(connection);
    }

    public static void displayMainMenu() {
        System.out.println("Welcome to Fee Admin Portal");
        System.out.println("1. Manage Admins");
        System.out.println("2. Manage Mentors");
        System.out.println("3. Update CourseYear");
        System.out.println("4. Update Course Details");
        System.out.println("5. Detailed Report (based on comments)");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    public void handleMainMenuInput() {
        if (authenticateChiefAdmin()) {
            boolean exit = false;
            while (!exit) {
                displayMainMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                    	chiefadminController.manageAdmins();
                        break;
                    case 2:
                        
                    	chiefadminController.manageMentors();
                        break;
                    case 3:
                    	System.out.println("Enter newCourseYear:");
                		int newCourseYear=scanner.nextInt();
                		paymentDAO.updateCourseYear(newCourseYear);
                		studentDAO.updateCourseYear(newCourseYear);
                		break;
                    case 4:
                        
                    	chiefadminController.updateCourseDetails();
                        break;
                    case 5:
                    	generateDetailedReport.generateDetailedReport();
                        break;
                    case 0:
                        System.out.println("Exiting Fee Admin Portal.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } else {
            System.out.println("Authentication failed. Exiting Fee Admin Portal.");
        }
    }

    private boolean authenticateChiefAdmin() {
        System.out.println("Please enter your chief admin ID:");
        String chiefAdminId = scanner.nextLine();
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        try {
            String query = "SELECT * FROM ChiefAdmin WHERE Chief_id = ? AND username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, chiefAdminId);
            statement.setString(2, username);
            statement.setString(3, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
}
