package Views;

import java.sql.SQLException;
import java.util.Scanner;

public class FeePortal {
    private Scanner scanner;

    public FeePortal() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        System.out.println("Welcome to Fee Portal");
        System.out.println("1. Access CAO Portal");
        System.out.println("2. Access Fee Admin Portal");
        System.out.println("3. Access Mentor Portal");
        System.out.println("4. Access Student Portal");
        System.out.println("0. Exit");
        System.out.println("Please enter your choice:");
    }

    public void handleMainMenuInput() {
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
            	navigateToChiefAdminPortal();
                break;
            case 2:
               
            	navigateToAdminPortal();
                break;
            case 3:
            	navigateToMentorPortal();
                break;
            case 4:
			try {
				navigateToStudentPortal();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
                break;
            case 0:
                System.out.println("Exiting Fee Portal");
                
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    
    private void navigateToStudentPortal() throws SQLException {
		
		StudentPortal studentPortal=new StudentPortal();
		studentPortal.handleMainMenuInput();
	}

	private void navigateToChiefAdminPortal() {
		
    	 ChiefAdminPortal chiefadminPortal = new ChiefAdminPortal();
    	 chiefadminPortal.handleMainMenuInput();
		
	}

	private void navigateToMentorPortal() {
        MentorPortal mentorPortal = new MentorPortal();
        mentorPortal.handleMainMenuInput();
      
    }
    private void navigateToAdminPortal() {
        AdminPortal adminPortal = new AdminPortal();
        adminPortal.handleMainMenuInput();
    }

}
