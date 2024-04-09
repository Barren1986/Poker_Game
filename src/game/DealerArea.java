package game;

import cards.Card;
import hand.Hand;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import player.Player;

public class DealerArea extends HBox{
	
	//Who does the dealer area belong to?
	private Player dealer;
	
	//The 3 cards holders (using a pane so we can style it
	private HBox cardBack;  //To mimic a deck face down
	private HBox discardHolder; //To mimic the discard pile

	//For Tioli only
	private HBox tioliHolder; //To show the card to decide on
	
	//Image views
	private ImageView cardBackView;
	private ImageView discardHolderView;
	
	//For Tioli
	private ImageView tioliHolderView;
	
	//To track if Tioli
	private boolean tioli = false;

	public DealerArea(Player dealer, String gameType) {
		//Note which player owns this object
		this.dealer = dealer;
		
		tioli = (gameType == "tioli") ? true : false;
		buildArea();
	}
	
	private void buildArea() {
		//Create the display for the face down deck
		createCardBackArea(); 
		
		//Create the TIOLI holder
		if(tioli) createTioliHolder();
		
		//Create the discard pile
		createDiscardHolder();
		
		//Now add them all the the HBox (using 'this')
		if(tioli) {
			this.getChildren().addAll(cardBack, tioliHolder, discardHolder);			
		} else {
			this.getChildren().addAll(cardBack, discardHolder);			
		}

		
	}
	
	private void createCardBackArea() {
		//Instantiate the HBox
		cardBack = new HBox();
		
		//Instantiate the ImageView
		cardBackView = new ImageView();
		
		//AddView to HBox
		cardBack.getChildren().add(cardBackView);
		
		//Now add an image to the pane 
		setCardBack("red");
		
		//Style HBox which holds our face down deck
		cardBack.setAlignment(Pos.CENTER);
		
		//Use some CSS to style the HBox
		styleCardHolders(cardBack);
		

	}

	private void createDiscardHolder() {
		//Instantiate the HBox
		discardHolder = new HBox();
		
		//Instantiate the ImageView
		discardHolderView = new ImageView();
		
		//Add view to HBox
		discardHolder.getChildren().add(discardHolderView);
		
		//Style HBox which holds our face down deck
		discardHolder.setAlignment(Pos.CENTER);
		
		//Use some CSS to style the HBox
		styleCardHolders(discardHolder);
		
	}
	
	private void createTioliHolder() {
		//Instantiate the HBox
		tioliHolder = new HBox();

		tioliHolderView = new ImageView();
		//Style HBox which holds our face down deck
		tioliHolder.setAlignment(Pos.CENTER);
		
		//Use some CSS to style the HBox
		styleCardHolders(tioliHolder);
		
	}


	private void styleCardHolders(Pane paneToStyle) {
		String cssLayout = 
				"-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n";

		paneToStyle.setStyle(cssLayout);
		paneToStyle.setPrefWidth(100);
		paneToStyle.setPrefHeight(120);
		paneToStyle.setMinHeight(USE_PREF_SIZE);
		paneToStyle.setMinWidth(USE_PREF_SIZE);
	}

	//Add the card back image to the the pane
	public void setCardBack(String color) {
	
		//Add the card image to the cardBackView object
		String imageURL = "file:images/card/" + color + ".png";
		cardBackView.setImage(new Image(imageURL));
	}

	protected void showDiscardedCard(Card card) {

		//Get the card image URL
		String cardImageURL =  "file:images/card/" + card.getCardImage();
		
		//Display the card image
		discardHolderView.setImage(new Image(cardImageURL));
	}
	

	//Add the Tioli card image to the pane
	protected void showTioliCard() {
		
		//Get the image URL from the dealer's hand
		Hand dealerHand = dealer.getHand();
		Card[] dealerCard = dealerHand.getCards();  //To use to get the image url
		String imageURL; //To hold the image url
		
		//The dealer hand only has 1 card
		imageURL =  "file:images/card/" + dealerCard[0].getCardImage(); //Get the cardImage value from the card object
			
		//Put the image in the TioliHolder
		tioliHolderView.setImage(new Image(imageURL));
	}
	
	//Assignment 3.2
	protected void removeTioliImage() {
		tioliHolderView.setImage(null);
	}

	//Assignment 3.2
	protected void removeDiscardImage() {
		discardHolderView.setImage(null);
	}

}
