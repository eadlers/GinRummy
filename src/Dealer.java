import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Dealer {

    private ArrayList<Card> deck;                                                  //This deck will also be the stock pile
    private Stack<Card> discardPile;

    //Purpose: Constructor
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Dealer instance is created with a new deck containing 52 cards
    public Dealer() {
        this.deck = new ArrayList<>(createDeck());
        discardPile = new Stack<>();
    }

    //Purpose: Shuffle the deck and give 10 cards to the player
    //Assumptions: None
    //Inputs: ArrayList<Card> deck : deck of cards the dealer has
    //Post-conditions: Cards have been dealt and original deck has 10 less cards now
    public ArrayList<Card> dealCards(ArrayList<Card> deck) {
        ArrayList<Card> playerHand = new ArrayList<>();
        Collections.shuffle(deck);
        for (int i = 0; i < 10; i++) {
            playerHand.add(deck.get(0));
            deck.remove(0);
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

    //Purpose: Push a card into the discard pile
    //Assumptions: None
    //Inputs: card: card to be pushed to the discard pile
    //Post-conditions: Card is added to top of the stack
    public void pushToDiscardPile(Card card) {
        discardPile.push(card);
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the current deck of the dealer
    public ArrayList<Card> getDeck() {
        return deck;
    }

    private ArrayList<Card> createDeck() {
        deck = new ArrayList<>(52);
        String[] ranks = {"Ace" , "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        int rankNumber = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                String name = rank + " of " + suit;
                deck.add(new Card(name, rank, suit, rankNumber));
                rankNumber++;
            }
        }
        return deck;
    }


}
