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
        getPlayerName(model.getPlayer(), model.getComputerPlayer());
        model.storeNames(model.getPlayer().getName(), model.getComputerPlayer().getName());
        while (true) {
            view.displayData("New turn has begun");
            checkStock();                                                                               //Check if stockpile is empty
            drawStock(model.getComputerPlayer());
            showHand(model.getComputerPlayer());
            model.getDealer().pushToDiscardPile(model.getComputerPlayer().getHand().remove(0));  //For now computer player will always discard it's first card
            if (askDiscard()) {                                                                        //Draw from top of the discard or stockpiles
                drawDiscard(model.getPlayer());
            } else {
                drawStock(model.getPlayer());
            }
            discard(model.getPlayer());
            findDeadwood(model.getPlayer(), model.getComputerPlayer());
            if (allMelds(model.getPlayer())) {                                                          //Game ends if player has all melds
                winMessage(model.getPlayer());
            } else if (allMelds(model.getComputerPlayer())) {
                winMessage(model.getComputerPlayer());
            }
            knock(model.getPlayer(), model.getComputerPlayer(), model.getDealer());                     //Ask and knock if desired
            if (getAnswer("Does " + model.getPlayer().getName() + " wish to end the game? Enter Y for yes or N for no ")) {
                view.displayData(model.getPlayer().getName() + " chose to exit the game, thank you for playing ");
                System.exit(0);
            }
            if (model.getPlayer().getScore() >= 100) {
                winMessage(model.getPlayer());
            } else if (model.getComputerPlayer().getScore() >= 100) {
                winMessage(model.getComputerPlayer());
            }
        }
    }



    //Purpose: Find melds and set deadwood score for two players
    //Assumptions: Calls other methods in Controller class
    //Inputs: player1: GinRummy player
    //        player2: GinRummy player, in this case it is the computer player
    //Post-conditions: Deadwood score for both players is updated. Melds are displayed
    private void findDeadwood(Player player1, Player player2) {
        findMeld(player1);
        player1.setDeadwoodScore(calculateDeadwood(player1));                  //Set deadwood score of player
        view.displayData(player1.getName() + " has deadwood score " + player1.getDeadwoodScore());
        view.displayData(player1.getName() + " has total score " + player1.getScore());
        System.out.println();
        findMeld(player2);
        player2.setDeadwoodScore(calculateDeadwood(player2));   //Set deadwood score of computer player
        view.displayData((player2.getName() + " has total score " + player2.getScore()));
    }


    //Purpose: Ask and simulate knocking between two players
    //Assumptions: None
    //Inputs: player: player who must choose to knock
    //        player2: second player, in our case computer player
    //        dealer: dealer of the match
    //Post-conditions: Winner has score variable updated, hands are reset, dealer gets new deck
    private void knock(Player player, Player player2, Dealer dealer) {
        if (getAnswer("Does " + player.getName() + " wish to knock? Enter Y for yes or N for no ")) {
            if (player.getDeadwoodScore() < player2.getDeadwoodScore()) {
                view.displayData(player.getName() + " wins the hand");
                player.incrementScore(player2.getDeadwoodScore() - player.getDeadwoodScore());
            } else if (player.getDeadwoodScore() > player2.getDeadwoodScore()) {
                view.displayData(player2.getName() + " wins the hand");
                player2.incrementScore(player.getDeadwoodScore() - player2.getDeadwoodScore());
            }
            dealer.knock();
            player.newHand(dealer);
            player2.newHand(model.getDealer());
            player.resetDeadwood();
            player2.resetDeadwood();
        }
    }


    //Purpose: State the winner and end the game
    //Assumptions: Player has won
    //Inputs: player: the player who has won
    //Post-conditions:  Displays the winner and ends the game
    private void winMessage(Player player) {
        showHand(player);
        showMeld(player);
        view.displayData(player + " won with a score of  " + model.getPlayer().getScore());
        endMessage();
    }

    //Purpose:Get the players name
    //Assumptions: Player name is not the same as computer player
    //Inputs: None
    //Post-conditions: name variable for player will be updated
    private void getPlayerName(Player player1, Player player2) {
        String name;
        do {
            name = view.getInput("Welcome to Gin Rummy! What is your name?");
        } while (name.equals(player2.getName()));
        player1.setName(name);
    }

    //Purpose: Check if player wants to quit the application or start a new game
    //Assumptions: Game has ended
    //Inputs: None
    //Post-conditions: Starts a new game if player chooses to or ends the application
    private void endMessage() {
        if (getAnswer("Does " + model.getPlayer().getName() + " wish to start a new game? Enter Y for yes or N for no ")) {
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
        int deadwoodScore = 0;
        for (Card card : player.getHand()) {
            if (player.getMelds().contains(card)) {
                continue;
            }
            switch(card.getRank()) {
                case "Ace":
                    deadwoodScore += 1;
                    break;
                case "Jack":
                case "Queen":
                case "King":
                    deadwoodScore += 10;
                    break;
                default:
                    deadwoodScore += Integer.parseInt(card.getRank());
            }
        }
        return deadwoodScore;
    }


    //Purpose: Find and display the meld the player's hand
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Displays the melds the player has in the melds variable
    private void showMeld(Player player) {
        view.displayData("Melds for " + player.getName() + " are ");
        displayMeld(player.getMelds());
    }

    private void findMeld(Player player) {
        HashSet<Card> rankMeld = findRankMeld(player.getHand());
        view.displayData(player.getName() + "'s Rank melds are: ");
        displayMeld(rankMeld);
        HashSet<Card> runMeld = findRunMeld(player.getHand(), rankMeld);
        view.displayData(player.getName() + "'s total melds are: ");
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
        view.displayData(player.getName() + " has hand");
        for (int i = 0; i < player.getHand().size(); i++) {
            view.displayData((i + 1) + ". [" + player.getHand().get(i).getName() + "] ");
        }
        view.displayData("\n");
    }

    //Purpose: Ask the player a yes/no question
    //Assumptions: None
    //Inputs: message: The question of which the user will answer yes/no to
    //Post-conditions: Returns true if the player says yes, false otherwise
    private boolean getAnswer(String message) {
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

    //Purpose: Check if the stockpile is empty
    //Assumptions: None
    //Inputs: None
    //Post-conditions: Ends the game if the stockpile is empty
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

    //Purpose: Let the player draw a card from the stockpile
    //Assumptions: Stock pile is not empty
    //Inputs: player: The player who wishes to draw a card
    //Post-conditions: Removes a card from the stockpile and adds it to the player's hand
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
        model.getDealer().pushToDiscardPile(player.getHand().remove(answer - 1));
    }

    //Purpose: Check if a player has its hand full of melds
    //Assumptions: None
    //Inputs: player: Player whose hand must be checked
    //Post-conditions: Returns true if the player's melds are 10 (hand size), else returns false
    private boolean allMelds(Player player) {
        return player.getMelds().size() >= 10;
    }

}

