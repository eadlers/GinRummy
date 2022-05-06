import java.util.Scanner;

public class View {

    private ViewInput input;
    private ViewOutput output;

    public View() {
        input = new ViewInput();
        output = new ViewOutput();
    }

    //Purpose: Get input from the player
    //Assumptions: None
    //Inputs: prompt: Text we want to show the player
    //Post-conditions: Gets input from player and returns it as a string
    public String getInput(String prompt) {
        output.displayText(prompt);
        return input.getInput();
    }

    //Purpose: Display string data
    //Assumptions: None
    //Inputs: text: string to be displayed
    //Post-conditions: Calls the viewOutput class in order to display a string message to the user
    public void displayData(String text) {
        output.displayText(text);
    }

}
