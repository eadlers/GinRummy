import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cards;

    //Purpose: Getter method
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the cards arrayList in the of the deck
    public ArrayList<Card> getCards() {
        return cards;
    }

    //Purpose: Constructor method in order to create the initial deck of cards
    //Assumptions: None
    //Inputs: None
    //Post-Conditions: ArrayList with 52 cards is created, each with a different combination of possible ranks and suits
    public Deck() {
        cards = new ArrayList<>(52);
        String[] ranks = {"Ace" , "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};
        int rankNumber = 0;
        for (String suit : suits) {
            for (String rank : ranks) {
                String name = rank + " of " + suit;
                cards.add(new Card(name, rank, suit, rankNumber));
                rankNumber++;
            }
        }
    }



}
