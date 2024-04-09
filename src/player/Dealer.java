package player;

import cards.Card;
import deck.Deck;
import hand.Hand;

public class Dealer extends Player{
	private Deck deck;
	
	public Dealer(Deck deck) {
		super("dealer", "Dealer" + randomDealer(), 0);
		this.deck = deck;
		prepareDeck();
	}
	
	private void prepareDeck() {
		deck.shuffleDeck();		
	}
	
	public void dealCard(Player player) {
		Hand tempHand = player.getHand();
		tempHand.addCard(deck.dealCard(0));
	}
	
	public void dealCard(Player player, int index) {
		Hand tempHand = player.getHand();
		tempHand.setCard(index, deck.dealCard(0));
	}
	
	public boolean reshuffleDeck() {
		
		//Get the deck's cards and usedCards 
		Card[] cards = deck.getCards();
		Card[] discardPile = deck.getUsedCards();
		
		//Now do the math
		int totalCards = cards.length + discardPile.length;
		int reshuffleCount = (int)(totalCards * .7);  //After 70% of deck is used
		if(cards.length < reshuffleCount) {
			deck.restack();
			return true;
		}
		
		return false;
	}
	
	public void gatherUsedCards(Player player) {
		player.getHand().discardAll(deck);
	}
	
	public void takeUsedCard(Player player, int index) {
		Card card = player.getHand().getCard(index);
		deck.addUsedCards(card);
	}

	public Deck getDeck() {
		return deck;
	}
	
	private static String randomDealer() {	
		String[] dealerName = {
				"John",
				"Joe",
				"Mary",
				"Eunice",
				"Pete",
				"Janie",
				"Jing",
				"Todd",
				"Bertha",
				"Marge"
		};
		
		return dealerName[(int)(Math.random()*dealerName.length)];
	}

}
