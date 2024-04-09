package gameobjects;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hand.Hand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PayoutTable extends VBox {

	private String[] bonusNames;
	private int[][] bonusOdds;
	
	int[][] payouts = {
			{0,1},   //High card
			{1, 1},   //pair Jacks or better
			{2, 1},  //2 pair
			{4, 1},  //3 of a kind
			{7, 1},  //straight
			{9, 1},  //flush
			{15, 1},  //full house
			{30, 1},  //4 of a kind
			{50, 1}, //straight flush
			{60, 1}, //5 of a kind
			{70,1}, //Wild Royal Flush
			{100, 1}, //4 deuces
			{200, 1}, //royal flush
	};

	
	private Label[] textList;
	private Label[] multiplierList;
	private Label[] oddsBaseList;
	private Label[] oddsTo;
	
	private ArrayList<GridPane> bonusText = new ArrayList<GridPane>();
	private String gameType;
	private String borderColor = "red";
	
	public PayoutTable(String gameType) {
		this.gameType = gameType;
		createBoard();
	}
	
	public PayoutTable(String gameType, String borderColor) {
		this(gameType);
		this.borderColor = borderColor;
	}
	
	private void createBoard() {
		setBonusData();
		
		//initialize arrays
		textList = new Label[bonusNames.length];
		multiplierList = new Label[bonusOdds.length];
		oddsBaseList = new Label[bonusOdds.length];
		oddsTo = new Label[bonusOdds.length];
		
		createLabels(); //Create and format labels
		createBonusText(); //Create each bonus object
		addToVBox(); //Create final object to be used
		styleVBox();  //Add some styling
	}


	private void setBonusData() {
		switch(gameType) {
			case "drawpoker":
			case "poker":
				String[] drawnames = {
						"Jacks or Better",
						"2 Pair",
						"3 of a Kind",
						"Straight",
						"Flush",
						"Full House",
						"4 of a Kind",
						"Straight Flush",
						"Royal Flush"
				};
				
				bonusNames = drawnames;
				int[][] drawodds = {
						{1, 1},   //pair Jacks or better
						{2, 1},  //2 pair
						{4, 1},  //3 of a kind
						{7, 1},  //straight
						{9, 1},  //flush
						{15, 1},  //full house
						{30, 1},  //4 of a kind
						{50, 1}, //straight flush
						{200, 1}, //royal flush
				};
				bonusOdds = drawodds;
				break;
			case "deuceswild":
				String[] wildnames = {
						"Jacks or Better",
						"2 Pair",
						"3 of a Kind",
						"Straight",
						"Flush",
						"Full House",
						"4 of a Kind",
						"Straight Flush",
						"5 of a Kind",
						"Wild Royal Flush",
						"4 Deuces",
						"Royal Flush"
				};
				
				bonusNames = wildnames;
				int[][] wildodds = {
						{1, 1},   //pair Jacks or better
						{2, 1},  //2 pair
						{4, 1},  //3 of a kind
						{7, 1},  //straight
						{9, 1},  //flush
						{15, 1},  //full house
						{30, 1},  //4 of a kind
						{50, 1}, //straight flush
						{60, 1}, //5 of a kind
						{70,1}, //Wild Royal Flush
						{100, 1}, //4 deuces
						{200, 1}, //royal flush
				};
				bonusOdds = wildodds;
				break;
			case "tioli":
				String[] tiolinames = {
						"2 Pair Aces High",
						"3 of a Kind",
						"Straight",
						"Flush",
						"Full House",
						"4 of a Kind",
						"Straight Flush",
						"Royal Flush"
				};
				
				bonusNames = tiolinames;
				int[][] tioliodds = {
						{2, 1},  //2 pair
						{4, 1},  //3 of a kind
						{7, 1},  //straight
						{9, 1},  //flush
						{15, 1},  //full house
						{30, 1},  //4 of a kind
						{50, 1}, //straight flush
						{200, 1}, //royal flush
				};
				bonusOdds = tioliodds;
				break;
				
			default:
				String[] defaultnames = {
						"Jacks or Better",
						"2 Pair",
						"3 of a Kind",
						"Straight",
						"Flush",
						"Full House",
						"4 of a Kind",
						"Straight Flush",
						"Royal Flush"
				};
				
				bonusNames = defaultnames;
				int[][] defaultodds = {
						{1, 1},   //pair Jacks or better
						{2, 1},  //2 pair
						{4, 1},  //3 of a kind
						{7, 1},  //straight
						{9, 1},  //flush
						{15, 1},  //full house
						{30, 1},  //4 of a kind
						{50, 1}, //straight flush
						{200, 1}, //royal flush
				};
				bonusOdds = defaultodds;
				break;

		}
		
		
	}

	public int getPayout(Hand hand, int wager) {
		int payout = -wager;
		int handRank = hand.getHandRank();
		switch(handRank) {
			case 1:
				payout = -wager;
				break;
			case 2:
				if(gameType == "tioli") {
					payout = -wager;  //Need 2 pair, aces high
				} else {
					//Check for jacks or better
					if(hand.getHandScore() >= 22) {
						payout = wager * (payouts[handRank-1][0] / payouts[handRank-1][1]);
					}
				}
				break;
			case 3:
				if(gameType == "tioli") {
					//Check for aces high
					if(hand.getAltCards()[0] == 14 && hand.getAltCards()[1] == 14) {  //1st two are aces
						payout = wager * (payouts[handRank-1][0] / payouts[handRank-1][1]);
					} else {
						payout = -wager;
					}
				} else {
					payout = wager * (payouts[handRank-1][0] / payouts[handRank-1][1]);
				}
				break;
			default:
				payout = wager * (payouts[handRank-1][0] / payouts[handRank-1][1]);
				break;
		}
		
		return payout;
	}
	

	private void createLabels() {
		for(int i=0; i<textList.length; i++) {
			textList[i] = new Label(bonusNames[i]);
			textList[i].setFont(Font.font("Arial", 18));
			textList[i].setAlignment(Pos.CENTER_LEFT);
			textList[i].setPadding((new Insets(2, 10, 2, 10)));
			
			int multiplier = bonusOdds[i][0];
			int oddsBase = bonusOdds[i][1];
			DecimalFormat formatter = new DecimalFormat("#,###");
			
			multiplierList[i] = new Label(formatter.format(multiplier));
			multiplierList[i].setFont(Font.font("Arial", 18));
			multiplierList[i].setAlignment(Pos.BASELINE_RIGHT);
			multiplierList[i].setPadding((new Insets(2, 10, 2, 10)));

			oddsBaseList[i] = new Label(formatter.format(oddsBase));
			oddsBaseList[i].setFont(Font.font("Arial", 18));
			oddsBaseList[i].setAlignment(Pos.BASELINE_RIGHT);
			oddsBaseList[i].setPadding((new Insets(2, 10, 2, 10)));
			
			oddsTo[i] = new Label("To");
			oddsTo[i].setFont(Font.font("Arial", 18));
			oddsTo[i].setAlignment(Pos.BASELINE_RIGHT);
			oddsTo[i].setPadding((new Insets(2, 10, 2, 10)));
		}
	}

	private void createBonusText() {		
		for(int i=0; i<bonusOdds.length; i++) {
			bonusText.add(new GridPane());
			bonusText.get(i).add(textList[i], 0, 0);
			bonusText.get(i).add(multiplierList[i], 1, 0);
			bonusText.get(i).add(oddsTo[i], 2, 0);
			bonusText.get(i).add(oddsBaseList[i], 3, 0);
			
			GridPane.setFillWidth(textList[i], true);
			textList[i].setPrefWidth(165);

			GridPane.setFillWidth(multiplierList[i], true);
			multiplierList[i].setPrefWidth(60);

			GridPane.setFillWidth(oddsTo[i], true);
			oddsTo[i].setPrefWidth(50);
		}		
	}

	private void addToVBox() {
		for(int i=bonusText.size()-1; i>=0; i--) {
			if(bonusOdds[i][0] != -1) {
				this.getChildren().add(bonusText.get(i));
			}
		}		
	}
	
	private void styleVBox() {
		if(borderColor.toLowerCase() != "none") {
			String cssLayout = 
					"-fx-border-color: " + borderColor + ";\n" +
	                "-fx-border-insets: 5;\n" +
	                "-fx-border-width: 2;\n" +
	                "-fx-border-style: solid;\n";
	
			this.setStyle(cssLayout);
			this.setPrefWidth(300);
			this.setPrefHeight(230);
			this.setMaxHeight(USE_PREF_SIZE);
		}		
	}
	
}
