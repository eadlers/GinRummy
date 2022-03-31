import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Dealer {

    private Deck deck;                                                  //This deck will also be the stock pile
    private Stack<Card> discardPile;

    //Purpose: Constructor
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Dealer instance is created with a new deck containing 52 cards
    public Dealer() {
        this.deck = new Deck();
        discardPile = new Stack<>();
    }

    //Purpose: Shuffle the deck and give 10 cards to the player
    //Assumptions: None
    //Inputs: Deck deck : original deck of cards created when we create a dealer object
    //Post-conditions: Cards have been dealt and original deck has 10 less cards now
    public ArrayList<Card> dealCards(Deck deck) {
        ArrayList<Card> playerHand = new ArrayList<>();
        Collections.shuffle(deck.getCards());
        for (int i = 0; i < 10; i++) {
            playerHand.add(deck.getCards().get(0));
            deck.getCards().remove(0);
        }
        return playerHand;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the current discard pile of the dealer
    public Stack<Card> getDiscardPile() {
        return discardPile;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the current deck of the dealer
    public Deck getDeck() {
        return deck;
    }


}
