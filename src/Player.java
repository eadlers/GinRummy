import java.util.ArrayList;
import java.util.HashSet;

public class Player {

    private int score;
    private int deadWood;               //deadWood score will be used for increment 5
    private ArrayList<Card> hand;
    private HashSet<Card> melds;
    private String name;

    //Purpose: Constructor for a player
    //Assumptions: Dealer has been created
    //Inputs:
    // dealer: dealer in charge of the cards
    //Post-conditions: Player instance has been created
    public Player(Dealer dealer) {
        this.hand = new ArrayList<>(dealer.dealCards(dealer.getDeck()));
        score = 0;
        deadWood = 0;
        melds = new HashSet<>();
    }

    public Player(Dealer dealer, String name) {
        this.hand = new ArrayList<>(dealer.dealCards(dealer.getDeck()));
        score = 0;
        deadWood = 0;
        melds = new HashSet<>();
        this.name = name;
    }

    //Purpose: Simulate a player knocking his cards and getting 10 new ones in case his score is still not 100
    //Assumptions: Game is in progress and a dealer has already been created
    //Inputs: dealer: Dealer of the game who has the current deck
    //Post-conditions: Player's hand is cleared and set to a new, different one
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


    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns HashSet containing the meld cards of the player
    public HashSet<Card> getMelds() {
        return melds;
    }

    //Purpose: Setter
    //Assumptions: None
    //Inputs: HashSet<Card> melds: HashSet to be set for the player's melds
    //Post-conditions: Sets the HashSet
    public void setMelds(HashSet<Card> melds) {
        this.melds = melds;
    }

    //Purpose: Setter / Will be used in increment 5
    //Assumptions: None
    //Inputs: deadWood: int value used to set deadwood of player
    //Post-conditions: Sets the deadwood variable to the parameter int
    public void setDeadWood(int deadWood) {
        this.deadWood = deadWood;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
