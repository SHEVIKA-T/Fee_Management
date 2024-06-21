package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GenerateDetailedReport {
    private Scanner scanner;
    private Connection connection;
   
    public GenerateDetailedReport(Connection connection) {
        this.scanner = new Scanner(System.in);
        this.connection = connection;
    }

    public void generateDetailedReport() {
        System.out.println("Select the report for which you want to generate a detailed report:");
        System.out.println("1. PreFeeReport");
        System.out.println("2. CurrentYearReport");

        int reportChoice = scanner.nextInt();
        scanner.nextLine(); 

        switch (reportChoice) {
            case 1:
                PreFeeReport();
                break;
            case 2:
                CurrentYearReport();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void PreFeeReport() {
        System.out.println("Choose the condition for detailed report:");
        System.out.println("1. Within due and balance remaining");
        System.out.println("2. Within due and no balance");
        System.out.println("3. Overdue and balance outstanding");
        System.out.println("4. Overdue but cleared");

        int conditionChoice = scanner.nextInt();
        scanner.nextLine();

        switch (conditionChoice) {
            case 1:
                try {
                    withinDueAndBalanceRemaining();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    WithinDueAndNoBalance();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    OverdueAndBalanceOutstanding();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 4:
                try {
                    OverdueButCleared();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }


    private void CurrentYearReport() {
        System.out.println("Choose the condition for detailed report:");
        System.out.println("1. Within due and balance remaining");
        System.out.println("2. Within due and no balance");
        System.out.println("3. Overdue and balance outstanding");
        System.out.println("4. Overdue but cleared");

        int conditionChoice = scanner.nextInt();
        scanner.nextLine(); 

        switch (conditionChoice) {
            case 1:
                try {
                    withinDueAndBalanceRemaining1();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    WithinDueAndNoBalance1();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    OverdueAndBalanceOutstanding1();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            case 4:
                try {
                    OverdueButCleared1();
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e.getMessage());
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void withinDueAndBalanceRemaining() throws SQLException {
        String query = "SELECT * FROM PreFeeReport WHERE Comments = 'Within due and balance remaining'";
        printResultSet(query);
    }

    public void WithinDueAndNoBalance() throws SQLException {
        String query = "SELECT * FROM PreFeeReport WHERE Comments = 'Within due and no balance'";
        printResultSet(query);
    }

    public void OverdueAndBalanceOutstanding() throws SQLException {
        String query = "SELECT * FROM PreFeeReport WHERE Comments = 'Overdue and balance outstanding'";
        printResultSet(query);
    }

    public void OverdueButCleared() throws SQLException {
        String query = "SELECT * FROM PreFeeReport WHERE Comments= 'Overdue but cleared'";
        printResultSet(query);
    }
    public void withinDueAndBalanceRemaining1() throws SQLException {
        String query = "SELECT * FROM payment WHERE Comments = 'Within due and balance remaining'";
        printResultSet(query);
    }

    public void WithinDueAndNoBalance1() throws SQLException {
        String query = "SELECT * FROM payment WHERE Comments = 'Within due and no balance'";
        printResultSet(query);
    }

    public void OverdueAndBalanceOutstanding1() throws SQLException {
        String query = "SELECT * FROM payment WHERE Comments = 'Overdue and balance outstanding'";
        printResultSet(query);
    }

    public void OverdueButCleared1() throws SQLException {
        String query = "SELECT * FROM payment WHERE Comments= 'Overdue but cleared'";
        printResultSet(query);
    }
    private void printResultSet(String query) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.println("Printing results...");
            while (resultSet.next()) {
                System.out.println("Student ID: " + resultSet.getString("stu_id"));
                System.out.println("Last Paid Fee ID: " + resultSet.getString("lastpaidfee_id"));
                System.out.println("Course ID: " + resultSet.getInt("course_id"));
                System.out.println("Course Year: " + resultSet.getInt("course_year"));
                System.out.println("Total Fee: " + resultSet.getInt("Total_fee"));
                System.out.println("Fee Status: " + resultSet.getString("fee_status"));
                System.out.println("Fee Balance: " + resultSet.getInt("fee_balance"));
                System.out.println("Comments: " + resultSet.getString("comments"));
                System.out.println("-----------------------------------");
            }
        }
    }


    
}
