package cards;

public class StandardCard extends Card {

	private String suit;  //spades (s), hearts (h), diamonds (d), clubs (c)
	private int suitIndex; //spades (0), hearts (1), diamonds (2), clubs (3)
	private int cardRank;    //Rank within the suit

	public StandardCard(int cardNumber) {
		super(cardNumber);
	}
	
	@Override
	public void createCard() {
		this.suitIndex = (int)((cardNumber-1) / 13);
		this.cardRank = ((cardNumber-1) % 13) + 1;
		
		setSuit();
		setCardValues();
		
		setCardImage();
	}

	private void setSuit() {
		switch(suitIndex) {
			case 0:
				this.suit = "s";
				this.color = "b";
				break;
			case 1:
				this.suit = "h";
				this.color = "r";
				break;
			case 2:
				this.suit = "d";
				this.color = "r";
				break;
			case 3:
				this.suit = "c";
				this.color = "b";
				break;
		}
	}

	private void setCardValues() {
				
		switch(this.cardRank) {
	    	case 1:
	    		this.face = "A";
	    		break;
	      case 2:
	      case 3:
	      case 4:
	      case 5:
	      case 6:
	      case 7:
	      case 8:
	      case 9:
	    	  this.face = Integer.toString(cardRank);
	    	  break;
	      case 10:
	    	  this.face = "T";
	    	  break;
	      case 11:
	    	  this.face = "J";
	    	  break;
	      case 12:
	    	  this.face = "Q";
	    	  break;
	      case 13:
	    	  this.face = "K";
	    	  break;
	      case 14:
	    	  this.face = "A";
	    	  break;
	    }
		
	}

	@Override
	public String toString() {
		return face + suit; 
	}

	public String getSuit() {
		return suit;
	}

	public int getSuitIndex() {
		return suitIndex;
	}

	public int getCardRank() {
		return cardRank;
	}
	
}
