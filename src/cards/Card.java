package cards;

public abstract class Card {
	protected int cardNumber; //Position in deck, start at 1
	
	protected String color; //Black (b) or red (r)
	protected String face;         //Card face
	protected String cardImage;

	private static int cardsCreated = 0;
	
	public Card(int cardNumber) {
		this.cardNumber = cardNumber;
		createCard();
		
		cardsCreated++;
	}
	
	public abstract void createCard();

	protected void setCardImage() {
		this.cardImage = (cardNumber) + ".png";
	}

	//Getters
	public int getCardNumber() {
		return cardNumber;
	}

	public String getFace() {
		return face;
	}

	public String getColor() {
		return color;
	}

	public String getCardImage() {
		return cardImage;
	}

	public static int getCardsCreated() {
		return cardsCreated;
	}
	
}
