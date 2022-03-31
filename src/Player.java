import java.util.ArrayList;
import java.util.HashSet;

public class Player {

    private int score;
    private int deadWood;
    private ArrayList<Card> hand;
    private HashSet<Card> melds;

    //Purpose: Constructor for a player
    //Assumptions: Dealer has been created
    //Inputs:
    // Dealer dealer: dealer in charge of the cards
    //Deck deck: Deck of cards, can be original or modified one with current cards
    //Post-conditions: Player instance has been created
    public Player(Dealer dealer, Deck deck) {
        this.hand = new ArrayList<>(dealer.dealCards(deck));
        score = 0;
        deadWood = 0;
        melds = new HashSet<>();
    }

    //Purpose: Simulate a player knocking his cards and getting 10 new ones in case his score is still not 100
    //Assumptions: Game is in progress and a dealer has already been created
    //Inputs: Dealer dealer: Dealer of the game who has the current deck
    //Post-conditions: Hand is cleared and new cards are given to the player
    public void knock(Dealer dealer) {
        hand.clear();
        setHand(dealer.dealCards(dealer.getDeck()));
    }



    //Purpose: Getter method
    //Assumptions: None
    //Inputs: None
    //Post-conditions: returns the current total score of the player
    public int getScore() {
        return score;
    }


    //Purpose: To increment the score by a certain amount
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Sets the score of the player to a specified amount
    public void incrementScore(int score) {
        this.score += score;
    }

    //Purpose: Setter method
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Sets the score of
    public void setScore(int score) {
        this.score = score;
    }

    //Purpose: Getter method
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the hand of the player
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


    public HashSet<Card> getMelds() {
        return melds;
    }

    public void setMelds(HashSet<Card> melds) {
        this.melds = melds;
    }

    public int getDeadWood() {
        return deadWood;
    }

    public void setDeadWood(int deadWood) {
        this.deadWood = deadWood;
    }
}
