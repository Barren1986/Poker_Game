package hand;

import java.util.ArrayList;

import cards.Card;
import cards.StandardCard;
import deck.Deck;
import helpers.PokerSolver;


public class Hand {
	private ArrayList<Card> cards;
	
	//For use by the PokerSolver
	private String handDescr = "";
	private int handScore = 0;
	private int handRank = 0;
	private int[] altCards;

	public Hand() {  
		//Only need to establish an empty cards ArrayList here
		cards = new ArrayList<Card>();	
	}
	
	public void addCard(Card dealtCard) {
		//Use .add to the card sent in the dealtCard parameter
		//to the cards attribute
		cards.add(dealtCard);
	}
	
	//Put a card into a specific element within the Hand
    public void setCard(int index, Card dealtCard){
        cards.set(index, dealtCard);
    }
	
	//Get a specific Card object at a specific index
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	//Get a specific Card object at a specific index and remove from Hand
	public Card removeCard(int index) {
		return cards.remove(index);
	}
	
	public void evaluateHand() {
		//Note: "this" represents the current object
		PokerSolver.evaluateHand(this, getHandString());
	}
	
	public void evaluateHand(String game) {
		//Note: "this" represents the current object
		PokerSolver.evaluateHand(this, getHandString(), game);
	}
	
	//This will need to be modified in Assignment 2.3
	private String[] getHandString() {
		String[] handString = new String[cards.size()]; 
		for(int i=0; i<cards.size(); i++) {
			if(cards.get(i) instanceof StandardCard)
				handString[i] = cards.get(i).getFace() + ((StandardCard)cards.get(i)).getSuit();			
		}

		return handString;
	}
	
	public int compareHand(Hand otherHand) {
		//First evaluate each hand
		evaluateHand();  //This hand object
		otherHand.evaluateHand();  		
		
		return gameResults(otherHand);
	}
	
	public int compareHand(Hand otherHand, String game) {
		//First evaluate each hand
		evaluateHand(game);  //This hand object
		otherHand.evaluateHand(game);  		
		
		return gameResults(otherHand);
	}
	
	private int gameResults(Hand otherHand) {
		int thisHandResult;
		
		int[] solverResults = PokerSolver.evaluatePokerGame(this, otherHand);
		
		//Set win lose or draw for this hand
		if(solverResults[0] == 1) {  //This hand wins
			thisHandResult = 1;  
		} else if(solverResults[0] == 0) { //This hand loses
			thisHandResult = -1;
		} else {  //Tie
			thisHandResult = 0;
		}
		return thisHandResult;
	}
	
	public void discard(Deck deck, int index) {
		
		//Remove the card designated by index from the hand
		Card tempCard = cards.remove(index);
		
		//Send the card to the deck using the Deck class' addDiscard method 
		deck.addUsedCards(tempCard);
	}
	
	public void discardAll(Deck deck) {
		
		//Loop until the cards attribute (the hand) is empty
		while(cards.size() > 0) {
			//Remove the card at the 0 index from the hand
			Card tempCard = cards.remove(0);
					
			//Send the card to the deck using the Deck class' addDiscard method 
			deck.addUsedCards(tempCard);
		}
	}
	
	@Override
	public String toString() {
		String handString = "";

		for(int i=0; i<cards.size(); i++) {			
			//Call to the Card object's getString to get Card info
			handString += cards.get(i) + " ";
		}
		
		return handString;
	}

	//The typical getters/setters
	public Card[] getCards() {
		
		//Transform the ArrayList to an array and return the array		
		Card[] cardArray = new Card[cards.size()];
		cards.toArray(cardArray);
		
		return cardArray; 
	}

	public int getHandRank() {
		return handRank;
	}

	public void setHandRank(int handRank) {
		this.handRank = handRank;
	}

	public String getHandDescr() {
		return handDescr;
	}

	public void setHandDescr(String handDescr) {
		this.handDescr = handDescr;
	}

	public int getHandScore() {
		return handScore;
	}

	public void setHandScore(int handScore) {
		this.handScore = handScore;
	}

	public int[] getAltCards() {
		return altCards;
	}

	public void setAltCards(int[] altCards) {
		this.altCards = altCards;
	}

}
