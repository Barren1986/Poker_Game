 package game;

import cards.Card;
import deck.Deck;
import gameobjects.GameOptions;
import gameobjects.PayoutTable;
import gameobjects.ScoreBoard;
import gameobjects.Wager;
import gameoutput.GameFile;
import hand.Hand;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import player.Dealer;
import player.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DrawPoker {
    
    // Attributes Area
    private Player player;
    private Dealer dealer;
    private PlayerArea playerArea;
    private DealerArea dealerArea;
    private BorderPane gameScreen = new BorderPane();
    private HBox header;
    private VBox centerSection = new VBox();
    private VBox rightSection = new VBox();
    private PayoutTable payoutTable;
    private Wager wager;
    private ScoreBoard scoreBoard;
    private Stage primaryStage;
    public GameOptions gameOptions;
    
    // Declare Button objects
    private Button btnDeal;
    private Button btnDraw;
    private Button btnExit;
    
    // Constructor
    public DrawPoker() { 	
        // Step 1: Instantiate a new Player object
        this.player = new Player("9765467", "FastFreddy", 2650);
        
        // Step 2: Instantiate a new Dealer object
        Deck deck = new Deck(52); // Deck class
        this.dealer = new Dealer(deck);
        
        // Step 3: Instantiate a new PlayerArea
        this.playerArea = new PlayerArea(this.player, 5, "drawPoker");	// Instantiate playerArea
        
        // Step 4: Instantiate a new PlayArea for dealer
        this.dealerArea = new DealerArea(this.dealer, "drawPoker");  // Instantiate dealerArea
        
        // Step 5: Instantiate a new PayoutTable object
        this.payoutTable = new PayoutTable("Draw Poker");
        
        // Step 6: Instantiate a new Wager object
        final int changeAmount = 100;
        this.wager = new Wager(this.player, changeAmount);
        
        // Step 7: Instantiate a new ScoreBoard object. Reminder that ScoreBoard needs player object
        this.scoreBoard = new ScoreBoard(player);
        
     // Instantiate the GameOptions object and add it to the appropriate section
        gameOptions = new GameOptions(playerArea, dealerArea);
        gameScreen.setLeft(gameOptions.getGameOptions());	// Accesses the GameOption directly 
  
        
        // Step 8: Call methods denoted as "called by constructor." DO NOT CALL SHOWGAME() just yet.
        createHeader();
        createRightSection();
        createCenterSection();
        showGame();
    }
    
    private void createHeader() {
        // Step 1: Instantiate a new Text object with the welcome message
        Text text = new Text("Welcome " + player.getName());
        
        // Step 2: Style the text
        text.setFont(Font.font("Arial", 37));
        
        // Step 3: Instantiate a new HBox and add the Text object to it
        header = new HBox(text);
        
        // Step 3: Center the header vertically and horizontally
        header.setAlignment(Pos.CENTER);
        
     // Add the header to the top of the screen
    	gameScreen.setTop(header);
        
    }

    
    // Methods called by constructor
    private void createCenterSection() {
        // Step 1: Instantiate centerSection - Already instantiated in the attribute
        
    	// Step 2: Add the dealerArea and playerArea attributes to the centerSection VBox
    	centerSection.getChildren().addAll(dealerArea, playerArea);

        
        // Step 3: Set alignment to center for the centerSection VBox
        centerSection.setAlignment(Pos.CENTER);
        
		// Set alignment to center for the playerArea within the centerSection VBox
        playerArea.setAlignment(Pos.CENTER);
        dealerArea.setAlignment(Pos.CENTER);
        
        // Add centerSection to gameScreen
        gameScreen.setCenter(centerSection);
        
        // Instantiate Button objects
        btnDeal = new Button("Deal");
        btnDraw = new Button("Draw");
        
        // Add btnDeal and btnDraw to an HBox
        HBox buttonBox = new HBox(btnDeal, btnDraw);
        buttonBox.setAlignment(Pos.CENTER);
        
        // Add the HBox containing buttons to the center section
        centerSection.getChildren().add(buttonBox);
        
        // Set font size of buttons
        btnDeal.setFont(new Font("Arial", 14));
        btnDraw.setFont(new Font("Arial", 14));
        
        // Set size of button
        btnDeal.setPrefWidth(75); // Set preferred width to 200 pixels
        btnDeal.setPrefHeight(75); // Set preferred height to 100 pixels
        
        btnDraw.setPrefWidth(75); // Set preferred width to 200 pixels
        btnDraw.setPrefHeight(75); // Set preferred height to 100 pixels
        
        // Button listener for btnDeal
        btnDeal.setOnAction(event -> {
            startDeal();
        });

        // Button listener for btnDraw
        btnDraw.setOnAction(event -> {
            drawCards();
        });
        
        // Button Listener for btnExit
        btnExit.setOnAction(event -> {
        	exitGame();
        });
        
    }
    
    // Method to exit the game
    private void exitGame() {
        primaryStage.close(); // Close the primary stage
    }    

	private void createRightSection() {
        // Already instantiated in the attribute
        rightSection.getChildren().add(payoutTable);
        rightSection.getChildren().add(wager);
        rightSection.getChildren().add(scoreBoard);
        // Add rightSection to gameScreen
        gameScreen.setRight(rightSection);
        
        // Instantiate Button object
        btnExit = new Button("Exit");
        
        // Change font size
        btnExit.setFont(new Font("Arial", 14));
        
        // Instantiate HBox object
        HBox buttonBox = new HBox(btnExit);
        
        // Align to center
        buttonBox.setAlignment(Pos.CENTER);
        rightSection.getChildren().add(buttonBox);
        
        // Set size of button
        btnExit.setPrefWidth(50); // Set preferred width to 200 pixels
        btnExit.setPrefHeight(50); // Set preferred height to 100 pixels
        
    }
    
    private void showGame() {
        //The usual stuff
        primaryStage = new Stage(); // Instantiate the Stage attribute
        Scene scene = new Scene(gameScreen, 1400, 600);
        primaryStage.setTitle("Ed's Draw Poker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
	// Method to handle the deal event
    private void startDeal() {
        // Step 1: Clear the previous cards if they exist
        clearCards();

        // Step 2: Deal 5 cards to the player
        dealPlayer();

        // Step 3: Evaluate the hand dealt and display the hand description
        evaluateHand();
        playerArea.showCards();
        playerArea.showHandDescr();

        // Step 4: Allow the player to select cards they want to replace
        playerArea.enableCardSelect();

     // Step 5: Send the selected cards to the discard pile and replace them with new cards
        boolean[] cardsSelected = playerArea.getCardSelected();

        for (int i = 0; i < cardsSelected.length; i++) {
            if (cardsSelected[i]) {
                // Get the card object from the player's hand at index i
                Card card = player.getHand().getCard(i);
                
                // Send the card to the discard pile
                dealerArea.showDiscardedCard(card);
                
                // Replace the discarded card with a new card
                dealer.dealCard(player, i); // Assuming you have a method to replace the card
            }
        }



        // Step 6: Determine if the deck should be reshuffled
        if (dealer.reshuffleDeck()) {
            dealerArea.removeDiscardImage();
        }

        // Step 7: Reset the scoreboard's win amount back to 0
        scoreBoard.setWinAmount(0);
    }    
    
 // Method to handle the draw event
    private void drawCards() {
        // Get the cardsSelected array from the PlayerArea
        boolean[] cardsSelected = playerArea.getCardSelected();

        // Loop through the cardsSelected array
        for (int i = 0; i < cardsSelected.length; i++) {
            // Check if the value at the loop index is true
            if (cardsSelected[i]) {
                // Remove the image of the card at the loop index from the player's hand
                playerArea.removeCardImage(i);

                // Get the card object from the player's hand at the loop index
                Card card = player.getHand().getCard(i);

                // Send the image of the card at the loop index to the discard pile
                dealerArea.showDiscardedCard(card);

                // Send the card object (not the image) at the loop index to usedCards
                dealer.takeUsedCard(player, i);

                // Deal the new card(s)
                dealer.dealCard(player, i); // Use the dealCard method specifying the index to place the card

                // Evaluate the hand
                evaluateHand();
            }
        }

        // Call the endHand event
        endHand();
    }

    
    
    public void endHand() {
    	// Hide the selected card images
    	playerArea.removeCardImage(0);

    	// Disable the selection of all card images
    	playerArea.disableCardSelect();

    	// Get the wager amount from the Wager object
    	int wagerAmount = wager.getWagerAmount();

    	// Get the payout amount from the PayoutTable object
    	int payoutAmount = payoutTable.getPayout(player.getHand(), wagerAmount);

    	// Update the player's bank amount
    	int currentBankAmount = player.getBank();
    	int updatedBankAmount = currentBankAmount + payoutAmount;
    	player.setBank(updatedBankAmount);

    	// Update the win amount on the ScoreBoard object
    	scoreBoard.setWinAmount(payoutAmount);

    	// Update the player's bank amount on the ScoreBoard object
    	scoreBoard.updateBank();
    	
    	// Call GameFile writeCSVData method to create data for files folder
    	GameFile.writeCSVData("playerdata.txt", player, updatedBankAmount);
    	
    }

	private void clearCards() {
        // Call gatherUsedCards() method of the dealer to move cards from player's hand to usedCards pile
        ((Dealer) dealer).gatherUsedCards(player);
    }

    private void dealPlayer() {
        // Deal five distinct cards to the player
        for (int i = 0; i < 5; i++) {
            ((Dealer) dealer).dealCard(player); // Deal a card to the player
        }
    }


    private void evaluateHand() {
    	player.getHand().evaluateHand();
    }
}
