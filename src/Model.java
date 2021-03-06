public class Model {

    private final Dealer dealer;
    private Player player;
    private Player computerPlayer;
    private DataStorage playerInfo;


    public Model() {
        dealer = new Dealer();
        player = new Player(dealer);
        computerPlayer = new Player(dealer, "Computer");
        playerInfo = new DataStorage("players.xml");
        dealer.getDiscardPile().add(dealer.getDeck().remove(0));
    }
    //Purpose: Getter method
    //Assumptions: None
    //Inputs: player: player object, for now we only have one
    //Post-conditions: Returns the player object
    public Player getPlayer() {
        return player;
    }

    //Purpose: Getter method
    //Assumptions: None
    //Inputs: dealer: dealer object, we will only have one dealer throughout the game
    //Post-conditions: Returns the dealer object
    public Dealer getDealer() {
        return dealer;
    }

    //Purpose: Getter method
    //Assumptions: None
    //Inputs: player: computerPlayer object
    //Post-conditions: Returns computerPlayer object
    public Player getComputerPlayer() {
        return computerPlayer;
    }

    public DataStorage getPlayerInfo() {
        return playerInfo;
    }

    //Purpose: Store the player's name in the DOM
    //Assumptions: None
    //Inputs: player1, player2: Players whose names will be stored
    //Post-conditions: DOM info is updated
    public void storeNames(String player1, String player2) {
        playerInfo.setPlayerName("1", player1);
        playerInfo.setPlayerName("2",player2);
        playerInfo.saveDOMtoXML();
    }

    //Purpose: Store the deadwood of each player in the DOM
    //Assumptions: None
    //Inputs: player1, player2: Players whose deadwood score will be stored
    //Post-conditions: DOM info is updated
    public void storeDeadwood(int player1, int player2) {
        playerInfo.setPlayerDeadwood("1", player1);
        playerInfo.setPlayerDeadwood("2", player2);
        playerInfo.saveDOMtoXML();
    }

    //Purpose: Store the score of each player in the DOM
    //Assumptions: None
    //Inputs: player1, player2: Players whose score will be stored
    //Post-conditions: DOM info is updated
    public void storeScore(int player1, int player2) {
        playerInfo.setPlayerScore("1", player1);
        playerInfo.setPlayerScore("2", player2);
        playerInfo.saveDOMtoXML();
    }

}
