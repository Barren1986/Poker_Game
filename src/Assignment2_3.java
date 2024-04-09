import cards.StandardCard;
import cards.UnoCard;
import deck.Deck;
import player.Dealer;
import player.Player;

public class Assignment2_3 {

	public static void main(String[] args) {
		//Create a dealer with a 52 Card deck
		Dealer dealer = new Dealer(new Deck(52));
		
		//Print the deck (the dealer already shuffled)		
		System.out.print("Before deal " + dealer.getDeck());
		
		//Create a player
		Player fastFreddy = new Player("9765467", "FastFreddy", 2650);
		
		//Deal five cards to the player and dealer
		for(int i=0; i<5; i++) {
			dealer.dealCard(fastFreddy);
			dealer.dealCard(dealer);
		}
		
		//Print the deck again	
		System.out.print("\n\nAfter deal " + dealer.getDeck());
		
		//Evaluate hands and find winner
		int winner = dealer.getHand().compareHand(fastFreddy.getHand());
		
		System.out.println("\n");
		
		//Display hands
		System.out.printf("%-22s", fastFreddy.getName() + "'s Hand: ");
		System.out.printf("%-20s", fastFreddy.getHand());
		System.out.printf("%-15s", fastFreddy.getHand().getHandDescr());
		
		System.out.println();

		System.out.printf("%-22s", dealer.getName() + "'s Hand: ");
		System.out.printf("%-20s",dealer.getHand());
		System.out.printf("%-15s", dealer.getHand().getHandDescr());

		System.out.println("\n");
		if(winner == 1) {
			System.out.println(dealer.getName() + " wins!");
		} else if(winner == -1) {
			System.out.println(fastFreddy.getName() + " wins!");
		} else {
			System.out.println(fastFreddy.getName() + " and " + dealer.getName() + " tied!");			
		}

		System.out.println();
		
		//Display the card ranks
		System.out.printf("%-20s", fastFreddy.getName() + "'s Card Ranks: ");
		for(int i=0; i<fastFreddy.getHand().getCards().length; i++) {
			System.out.print(((StandardCard)fastFreddy.getHand().getCards()[i]).getCardRank() + " ");		
		}
		
		System.out.println();
		
		System.out.printf("%-20s", dealer.getName() + "'s Card Ranks: ");
		for(int i=0; i<dealer.getHand().getCards().length; i++) {
			System.out.print(((StandardCard)dealer.getHand().getCards()[i]).getCardRank() + " ");		
		}
		
		//Send cards to used card pile
		dealer.gatherUsedCards(fastFreddy);
		dealer.gatherUsedCards(dealer);
		
		//Print the deck again
		System.out.print("\n\nAfter discard " + dealer.getDeck());
		
		//Restack the deck
		dealer.getDeck().restack();

		//Print the deck again
		System.out.print("\n\nAfter restack " + dealer.getDeck());

		//-----------------------------------
		//EC
		
		//Create dealer with an Uno deck
		Dealer unoDealer = new Dealer(new Deck(108, new UnoCard(0)));
		
		//Print the Uno deck
		System.out.print("\n\nUno " + unoDealer.getDeck());
		
	}

}
