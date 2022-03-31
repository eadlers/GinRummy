public class ViewOutput {

    //Purpose: Display text to the user
    //Assumptions: None
    //Inputs: text: string text to be displayed to the user
    //Post-conditions: displays the given text to the user
    public void displayText(String text) {
        System.out.println(text);
    }

    //Purpose: Display an object to the user
    //Assumptions: None
    //Inputs: o: object ot be displayed to the user
    //Post-conditions: Displays the given object to the user
    public void displayData(Object o ) {
        System.out.println(o);
    }
}
