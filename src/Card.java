public class Card {

    private final String name;          //Name is the rank and suit of the card, used for displaying the cards
    private final String rank;          //Instance variables name and suit will be used later on
    private final String suit;          //final because we can't change the cards after they're created
    private final int rankNumber;       //Used to determine the rank and suit of a card

    //Purpose: Constructor
    //Assumptions: None
    //Inputs:
    //  String name: combination of rank and card, will be used just for output to users laser on
    //  String rank: rank of the card
    //  String suit: suit of the card
    //Post-conditions: Card instance is created with a rank and a suit
    public Card(String name, String rank, String suit, int rankNumber) {
        this.name = name;
        this.rank = rank;
        this.suit = suit;
        this.rankNumber = rankNumber;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: returns the rank of the card
    public String getRank() {
        return rank;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the name of the card
    public String getName() {
        return name;
    }

    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the number of the rank of the card
    public int getRankNumber() { return rankNumber; }


    //Purpose: Getter
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns the suit of the card
    public String getSuit() {
        return suit;
    }

}
