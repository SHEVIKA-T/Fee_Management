package FeeManagment;
import Views.FeePortal;

public class Main {
    public static void main(String[] args) {
        FeePortal feePortal = new FeePortal();
        feePortal.displayMainMenu();
        feePortal.handleMainMenuInput();
    }
}

