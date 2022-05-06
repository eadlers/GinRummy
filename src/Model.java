public class Model {

    private final Dealer dealer;
    private Player player;
    private Player computerPlayer;


    public Model() {
        dealer = new Dealer();
        player = new Player(dealer);
        computerPlayer = new Player(dealer, "Computer");
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


}
