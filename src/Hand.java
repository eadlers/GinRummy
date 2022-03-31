import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> hand;

    //Purpose: Constructor
    //Assumptions: None
    //Inputs: ArrayList<Card> hand: cards the hand will contain
    //Post-conditions: Creates a hand object containing an ArrayList of cards
    public Hand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    //Purpose: Setter
    //Assumptions: None
    //Inputs: ArrayList<Card> hand: ArrayList with every card in the hand
    //Post-conditions: Sets the hand with a new ArrayList of cards
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    //Purpose: Clear the hand
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Hand ArrayList is empty
    public void clear() {
        hand.clear();
    }






}
