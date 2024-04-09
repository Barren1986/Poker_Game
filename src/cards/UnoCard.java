package cards;

public class UnoCard extends Card{
	private int drawAmount;
	private boolean reverse;
	private boolean wild;
	private boolean skip;

	public UnoCard(int cardNumber) {
		super(cardNumber);
	}
	
	@Override
	public void createCard() {
		
		setColor();
		setCardValues();
		
		super.setCardImage();  //Will execute in the Card class 
	}

	private void setColor() {
		if(cardNumber > 96) {
			setLastCardsColor();
			return;
		}
		
		int colorNum = (int)((cardNumber-1) / 24);
		
		switch(colorNum) {
			case 0:
				color = "r";
				break;
			case 1:
				color = "y";
				break;
			case 2:
				color = "g";
				break;
			case 3:
				color = "b";
				break;
		}
		
	}

	private void setLastCardsColor() {
		switch(cardNumber) {
			case 97:
				color = "r";
				break;
			case 98:
				color = "y";
				break;
			case 99:
				color = "g";
				break;
			case 100:
				color = "b";				
				break;
			default:  //For the 8 wild cards
				color = "w";
				break;				
		}
		
	}

	private void setCardValues() {
		if(cardNumber > 96) {
			setLastCardsValues();
			return;
		}
		
		//Set some defaults
		drawAmount = 0;  //Overridden below for draw 2 (12)
		wild = false;
		skip = false;  //Overridden by skip card (10)
		reverse = false; //Overridden by reverse card (11)
		
		
		int cardRank = ((cardNumber-1) % 12) + 1;
		
		switch(cardRank) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				face = Integer.toString(cardRank);
				break;
			case 10:
				face = "SK";
				skip = true;
				break;
			case 11:
				face = "RV";
				reverse = true;
				break;
			case 12:
				face = "D2";
				drawAmount = 2;
				break;
		}		
	}

	private void setLastCardsValues() {
		
		//Set some defaults
		drawAmount = 0;  //Overridden below for draw 4 cards
		wild = false;  //Overridden by last 8 cards
		skip = false;  
		reverse = false;
		
		switch(cardNumber) {
			case 97:  //The 4 0 cards
			case 98:
			case 99:
			case 100:
				face = "0";
				break;
			case 101:  //The 4 wild cards
			case 102:
			case 103:
			case 104:
				face = "WD";
				wild = true;
				break;
			default:  //For the last 4 draw 4 wild cards
				face = "DR4";
				wild = true;
				drawAmount = 4;
				break;				
		}		
	}

	@Override
	public String toString() {
		if(wild) {
			return face;
		} else {
			return face + color; 			
		}
	}
	

	public int getDrawAmount() {
		return drawAmount;
	}

	public boolean isReverse() {
		return reverse;
	}

	public boolean isWild() {
		return wild;
	}

	public boolean isSkip() {
		return skip;
	}

	
}
