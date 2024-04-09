package deck;

import java.util.ArrayList;

import cards.Card;
import cards.StandardCard;
import cards.UnoCard;

public class Deck {
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> usedCards = new ArrayList<Card>();
	
	public Deck(int deckSize) {
		
		for(int i=0; i<deckSize; i++) {
			cards.add(new StandardCard(i+1));
		}
	}
	
	public Deck(int deckSize, Card card) {
		
		for(int i=0; i<deckSize; i++) {
			if(card instanceof UnoCard) {
				cards.add(new UnoCard(i+1));
			} else {
				cards.add(new StandardCard(i+1));				
			}
		}
	}
	
	public void shuffleDeck() {
		Card cardTemp;
		int randomIndex;
		
		for(int i=0; i<cards.size(); i++) {
			randomIndex = (int)(Math.random() * cards.size());
			
			cardTemp = cards.get(i);
			cards.set(i, cards.get(randomIndex));
			cards.set(randomIndex, cardTemp);
		}
	}

	public void restack() {
		while(usedCards.size() > 0) {
			cards.add(usedCards.remove(0));
		}
		
		shuffleDeck();
	}
	
	//Return a single card object and remove from deck
	public Card dealCard(int index) {		
		return cards.remove(index);
	}
	
	//Return a single card object and leave in deck
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	public void addUsedCards(Card usedCard) {
		usedCards.add(usedCard);
	}
	
	//To return the entire deck
	public Card[] getCards() {
		//Converts arraylist to an array
		Card[] cardArray = new Card[cards.size()];
		cards.toArray(cardArray);
		
		return cardArray;
	}
	
	public Card[] getUsedCards() {
		//Converts arraylist to an array
		Card[] cardArray = new Card[usedCards.size()];
		usedCards.toArray(cardArray);
		
		return cardArray;
	}


	@Override
	public String toString() {
		
		String deckString = "Deck:\n";
		
		for(int i=0; i<cards.size(); i++) {
			if(i != 0 && i % 13 == 0)
				deckString += "\n";
			
			deckString += cards.get(i) + " ";
		}
		
		if(usedCards.size() > 0) {
			deckString += "\n\nUsed Cards:\n";
			
			for(int i=0; i<usedCards.size(); i++) {
				if(i != 0 && i % 13 == 0)
					deckString += "\n";
				
				deckString += usedCards.get(i) + " ";
			}		
		}
		
		
		return deckString;
	}
}
