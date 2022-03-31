import java.util.Scanner;

public class ViewInput {

    private Scanner scanner;

    //Purpose: Create PlayerInput object
    //Assumptions: None
    //Inputs: None
    //Post-conditions: PlayerInput object is created containing a Scanner object
    public ViewInput() {
        this.scanner = new Scanner(System.in);
    }

    //Purpose: Get input from the user
    //Assumptions: None
    //Inputs: None
    //Post-conditions: returns the String input from the user
    public String getInput() {
        return scanner.nextLine();
    }

}
