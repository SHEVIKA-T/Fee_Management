
package Views;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import Model.DBConnection;
import Controller.AdminController;
import Controller.GeneratePaidReport;
import Controller.GeneratePendingReport;

public class AdminPortal {
    private Scanner scanner;
    private Connection connection;
    private AdminController adminController;
    private GeneratePaidReport generatePaidReport;
    private GeneratePendingReport generatePendingReport;

    public AdminPortal() {
        this.scanner = new Scanner(System.in);
        this.connection = DBConnection.getConnection();
        this.adminController = new AdminController();
        this.generatePaidReport = new GeneratePaidReport(connection);
        this.generatePendingReport = new GeneratePendingReport(connection);
        
        
    }
    public void displayMainMenu() {
        System.out.println("Welcome to Admin Portal");
        System.out.println("1. Update Payment");
        System.out.println("2. View All Fee Reports");
        System.out.println("3. Student Payment History");
        System.out.println("4. View All Payment Histories");
        System.out.println("5. Generate Paid Status");
        System.out.println("6. Generate Pending Status");
        System.out.println("7. Update Password");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    public void handleMainMenuInput() {
        if (authenticateAdmin()) {
            boolean exit = false;
            while (!exit) {
                displayMainMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        adminController.updatePayment();
                        break;
                    case 2:
                        adminController.viewAllFeeReports();
                        break;
                    case 3:
                        System.out.println("Enter student ID for payment history:");
                        String studentId = scanner.next();
                        adminController.viewStudentPaymentHistory(studentId);
                        break;
                    case 4:
                        adminController.viewAllPaymentHistories();
                        break;
                    case 5:
                    	generatePaidReport.generatePaidStatus();
                        break;
                    case 6:
                    	 generatePendingReport.generatePendingStatus();
                        break;
                    case 7:
                        
                    	adminController.updatePassword();
                        break;
                    case 0:
                        System.out.println("Exiting Admin Portal.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } else {
            System.out.println("Invalid credentials. Exiting Admin Portal.");
        }
    }


    private boolean authenticateAdmin() {
        System.out.println("Please enter your admin ID:");
        String adminId = scanner.nextLine();
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();
        try {
            String query = "SELECT * FROM admin WHERE admin_id = ? AND username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, adminId);
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

