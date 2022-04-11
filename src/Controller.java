import java.util.ArrayList;
import java.util.HashSet;


public class Controller {

    private View view;
    private Model model;
    private MeldTracker meldTracker;

    public Controller() {
        view = new View();
        model = new Model();
        meldTracker = new MeldTracker();
    }

    //Purpose: Play the game of gin rummy until there is a winner
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Game is played and steps are displayed
    public void play() {
        model.getPlayer().setDeadWood(calculateDeadwood(model.getPlayer()));         //Set score of initial hand given to player
        boolean firstTurn = true;
        while (model.getPlayer().getScore() < 100) {
            view.displayData("New turn has begun");
            if (firstTurn) firstTurn = false;                                  //Do not have to calculate score if it is the first turn
            else {
                model.getPlayer().setDeadWood(calculateDeadwood(model.getPlayer()));
            }
            checkStock();                                                       //Check if stock pile is empty
            if (askDiscard()) {                                                //Draw from top of the discard or stock piles
                drawDiscard(model.getPlayer());
            } else {
                drawStock(model.getPlayer());
            }
            discard(model.getPlayer());
            findMeld(model.getPlayer());
            view.displayData("Player 1 has score " + model.getPlayer().getScore());
            if (allMelds(model.getPlayer())) {                                  //Game ends if player has all melds
                break;
            }
            if (checkStop("Do you wish to knock? Enter Y for yes or N for no ")) {
                model.getPlayer().knock(model.getDealer());                     //Knocking in increment 4 will just change the player's cards, new scoring will be added in increment 5
            }
            if (checkStop("Do you wish to end the game? Enter Y for yes or N for no ")) {
                view.displayData("Player chose to exit the game, thank you for playing ");
                System.exit(0);
            }
        }
        showHand(model.getPlayer());
        showMeld(model.getPlayer());
        view.displayData("Player1 won with a score of  " + model.getPlayer().getScore());
        endMessage();
    }


    //Purpose: Check if player wants to quit the application or start a new game
    //Assumptions: Game has ended
    //Inputs: None
    //Post-conditions: Starts a new game if player chooses to or ends the application
    private void endMessage() {
        if (checkStop("Do you wish to start a new game? Enter Y for yes or N for no ")) {
            Controller controller = new Controller();
            controller.play();
        } else {
            view.displayData("Thank you for playing ");
            System.exit(0);
        }
    }

    //Purpose: Calculate the score of the player given his current hand
    //Assumptions: Player has cards in his hand
    //Inputs: None
    //Post-conditions: Returns an int value of the score of the players hand
    private int calculateDeadwood(Player player) {
        //Find melds
        HashSet<Card> melds = findRankMeld(player.getHand());
        //hand.displayMeld(melds);
        for (Card card : player.getHand()) {
            if (melds.contains(card)) {
                continue;
            }
            switch(card.getRank()) {
                case "Ace":
                    player.incrementScore(1);
                    break;
                case "Jack":
                case "Queen":
                case "King":
                    player.incrementScore(10);
                    break;
                default:
                    player.incrementScore(Integer.parseInt(card.getRank()));
            }
        }
        return player.getScore();
    }


    //Purpose: Find and display the meld the player's hand
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Uses methods in Hand class to find and display the meld of the current hand of the player
    private void showMeld(Player player) {
        view.displayData("Melds are ");
        displayMeld(player.getMelds());
    }

    private void findMeld(Player player) {
        HashSet<Card> rankMeld = findRankMeld(player.getHand());
        view.displayData("Rank melds are ");
        displayMeld(rankMeld);
        HashSet<Card> runMeld = findRunMeld(player.getHand(), rankMeld);
        view.displayData("Run melds are ");
        displayMeld(runMeld);
        player.setMelds(runMeld);
    }

    //Purpose: Find the rank melds in a given hand
    //Assumptions: None
    //Inputs: hand: The hand of the player whose melds we want to find
    //Post-conditions: Returns a HashSet containing the rank melds
    private HashSet<Card> findRankMeld(ArrayList<Card> hand) {
        return meldTracker.findRankMeld(hand);
    }

    //Purpose: Find the run melds in a given hand
    //Assumptions: None
    //Inputs: hand: The hand of the player whose melds we want to find
    //Post-conditions: Returns a HashSet containing the run melds and the rank melds
    private HashSet<Card> findRunMeld(ArrayList<Card> hand, HashSet<Card> rankMeld) { return meldTracker.findRunMeld(hand, rankMeld); }



    //Purpose: Display the meld in a hand
    //Assumptions: None
    //Inputs: HashSet<String> melds: HashSet containing each meld
    //Post-conditions: Displays the rank of the melds, none if there are  no melds
    private void displayMeld(HashSet<Card> melds) {
        if (melds.isEmpty()) {
            view.displayData("None");
        } else {
            int count = 1;
            for(Card meld : melds) {
                view.displayData(count + ". [" + meld.getName() + "]");
                count++;
            }
        }
        view.displayData("\n");
    }

    //Purpose: Display the cards the player has
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Displays each card the player has (suit and rank)
    private void showHand(Player player) {
        view.displayData("Player has hand");
        for (int i = 0; i < player.getHand().size(); i++) {
            view.displayData((i + 1) + ". [" + player.getHand().get(i).getName() + "] ");
        }
        view.displayData("\n");
    }

    //Purpose: Ask the player a yes/no question
    //Assumptions: None
    //Inputs: message: The question which the user will answer yes/no to
    //Post-conditions: Returns true if the player says yes, false otherwise
    private boolean checkStop(String message) {
        String answer;
        do {
            answer = view.getInput(message);
        } while (!answer.equals("Y") && !answer.equals("N"));
        return answer.equals("Y");
    }



    //Purpose: Show the card on top of the discard pile and ask if they wish to draw form discard or stock
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Returns a boolean: True if they pick discard pile or false if they pick stock
    private boolean askDiscard() {
        String answer;
        showHand(model.getPlayer());
        do {
            if (!model.getDealer().getDiscardPile().isEmpty()) {
                view.displayData("Card on top of the discard pile is " + model.getDealer().getDiscardPile().peek().getName());
            } else {
                view.displayData("Discard pile is empty ");
            }
            answer = view.getInput("Do you wish to draw a card from the discard or stock pile? \n Enter D for discard or S for stock ");
        } while (!answer.equals("D") && !answer.equals("S"));
        return answer.equals("D");
    }

    //Purpose: Check if the stock pile is empty
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Ends the game if the stock pile is empty
    private void checkStock() {
        if (model.getDealer().getDeck().isEmpty()) {
            view.displayData("Stock pile is empty... game has ended");
            endMessage();
        }
    }

    //Purpose: Let the player draw a card from the discard pile
    //Assumptions: Stock pile is not empty
    //Inputs: player: The player who wishes to draw a card
    //Post-conditions: Pops the card from the discard stack and adds it to the player's hand, if the discard pile is empty then draw from stock
    private void drawDiscard(Player player) {
        if (model.getDealer().getDiscardPile().isEmpty()) {
            view.displayData("Discard pile is empty... you must draw a card from the stock pile ");
            drawStock(player);
        } else {
            player.getHand().add(model.getDealer().getDiscardPile().pop());
        }
    }

    //Purpose: Let the player draw a card from the stock pile
    //Assumptions: Stock pile is not empty
    //Inputs: player: The player who wishes to draw a card
    //Post-conditions: Removes a card from the stock pile and adds it to the player's hand
    private void drawStock(Player player) {
        player.getHand().add(model.getDealer().getDeck().remove(0));
    }

    //Purpose: Let the player choose which card the want to discard and remove it from their hand
    //Assumptions: None
    //Inputs: player: The player who wishes to discard a card
    //Post-conditions: The chosen card is removed from the players hand
    private void discard(Player player) {
        int answer;
        showHand(player);
        do {
            try {
                answer = Integer.parseInt(view.getInput("Enter the number of the card you want to discard"));
            } catch (Exception NumberFormatException) {
                answer = 100;
            }
        } while ( answer < 1 || answer > 11);
        model.getDealer().getDiscardPile().push(player.getHand().remove(answer - 1));
    }

    //Purpose: Check if a player has a hand full of melds
    //Assumptions: None
    //Inputs: player: Player whose hand must be checked
    //Post-conditions: Returns true if the player's melds are 10 (hand size), else returns false
    private boolean allMelds(Player player) {
        return player.getMelds().size() >= 10;
    }

}

