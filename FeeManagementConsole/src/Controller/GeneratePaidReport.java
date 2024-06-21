package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GeneratePaidReport {
    private Scanner scanner;
    private Connection connection;

    public GeneratePaidReport(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void generatePaidStatus() {
        System.out.println("Select the criteria for generating paid status:");
        System.out.println("1. Current Year Fee Report");
        System.out.println("2. Pre Fee Report");
        System.out.println("0. Back to main menu");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                generatePaidStatusOptions();
                break;
            case 2:
                generatePreFeeReportOptions();
                break;
            case 0:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void generatePaidStatusOptions() {
        System.out.println("Select the criteria for generating current year fee report:");
        System.out.println("1. All Report");
        System.out.println("2. Course Wise Report");
        System.out.println("0. Back to main menu");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                generatePaidStatusAllReport();
                break;
            case 2:
                generatePaidStatusCourseWiseReport();
                break;
            case 0:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
    private void generatePaidStatusAllReport() {
        try {
            String query = "SELECT * FROM payment WHERE fee_status = 'paid'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Paid Status All Report");
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String courseId = resultSet.getString("course_id");
                String feeStatus = resultSet.getString("fee_status");
                System.out.println("Student ID: " + stuId + ", Course ID: " + courseId + ", Fee Status: " + feeStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error generating paid status all report: " + e.getMessage());
        }
    }

    private void generatePaidStatusCourseWiseReport() {
        System.out.println("Enter the course ID:");
        String courseId = scanner.nextLine();
        try {
            String query = "SELECT * FROM payment WHERE course_id = ? AND fee_status = 'paid'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Paid Status Report for Course ID: " + courseId);
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeStatus = resultSet.getString("fee_status");
               
                System.out.println("Student ID: " + stuId + ", Fee Status: " + feeStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error generating paid status course-wise report: " + e.getMessage());
        }
    }


    private void generatePreFeeReportOptions() {
        System.out.println("Select the criteria for generating pre fee report:");
        System.out.println("1. Course Wise Report");
        System.out.println("2. Course Year Wise Report");
        System.out.println("3. Both Reports");
        System.out.println("0. Back to main menu");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        switch (choice) {
            case 1:
                generatePreFeeCourseWiseReport();
                break;
            case 2:
                generatePreFeeCourseYearWiseReport();
                break;
            case 3:
                generatePreFeeBothReports();
                break;
            case 0:
                System.out.println("Returning to the main menu.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void generatePreFeeCourseWiseReport() {
        System.out.println("Enter the course ID:");
        String courseId = scanner.nextLine();
        try {
            String query = "SELECT * FROM PreFeeReport WHERE course_id = ? AND fee_status = 'paid'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Pre Fee Report for Course ID: " + courseId);
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeStatus = resultSet.getString("fee_status");
                int courseYear=resultSet.getInt("course_year");
                System.out.println("Student ID: " + stuId + ", Fee Status: " + feeStatus+",Course Year: "+courseYear);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving pre fee report by course: " + e.getMessage());
        }
    }

    private void generatePreFeeCourseYearWiseReport() {
        System.out.println("Enter the course year:");
        int courseYear = scanner.nextInt();
        scanner.nextLine();
        try {
            String query = "SELECT * FROM PreFeeReport WHERE course_year = ? AND fee_status = 'paid'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, courseYear);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Pre Fee Report for Course Year: " + courseYear);
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeStatus = resultSet.getString("fee_status");
                System.out.println("Student ID: " + stuId + ", Fee Status: " + feeStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving pre fee report by course year: " + e.getMessage());
        }
    }

    private void generatePreFeeBothReports() {
        System.out.println("Enter the course ID:");
        String courseId = scanner.nextLine();
        System.out.println("Enter the course year:");
        int courseYear = scanner.nextInt();
        scanner.nextLine(); 
        try {
            String query = "SELECT * FROM PreFeeReport WHERE course_id = ? AND course_year = ? AND fee_status = 'paid'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, courseId);
            statement.setInt(2, courseYear);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Pre Fee Report for Course ID: " + courseId + " and Course Year: " + courseYear);
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String stuId = resultSet.getString("stu_id");
                String feeStatus = resultSet.getString("fee_status");
                System.out.println("Student ID: " + stuId + ", Fee Status: " + feeStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving pre fee report by course and course year: " + e.getMessage());
        }
    }

}
