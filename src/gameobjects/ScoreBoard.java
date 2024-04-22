package gameobjects;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import player.Player;

public class ScoreBoard extends VBox {
	
	private Label bankLabel = new Label("Bank: ");
	private Label winAmountLabel = new Label("Win Amount: ");
	private Label bankAmount = new Label();
	private Label winAmount = new Label("0");
	private GridPane[] scoreboard = new GridPane[2];
	private String borderColor = "blue";
	
	private Player player;
	
	public ScoreBoard(Player player) {
		this.player = player;
		
		buildScoreBoard();
	}
	
	public ScoreBoard(Player player, String borderColor)  {
		//Score board consists of the players bank and amount won 
		this.player = player;
		this.borderColor = borderColor;	
		
		buildScoreBoard();
	}

	public void setWinAmount(int amount) {
		winAmount.setText(formattedNumber(amount));
	}
	
	public void updateBank() {
		bankAmount.setText(formattedNumber(player.getBank()));
	}

	private void buildScoreBoard() {
		bankAmount.setText(formattedNumber(this.player.getBank()));
		
		createLabels();
		createScoreBoard();
		addToVBox(); //Create final object to be used
		styleVBox();		
	}

	private void createLabels() {
		bankLabel.setFont(Font.font("Arial", 18));
		bankLabel.setAlignment(Pos.CENTER_LEFT);
		bankLabel.setPadding((new Insets(5, 10, 5, 10)));
		
		bankAmount.setFont(Font.font("Arial", 18));
		bankAmount.setAlignment(Pos.CENTER_RIGHT);
		bankAmount.setPadding((new Insets(5, 10, 5, 10)));
		
		winAmountLabel.setFont(Font.font("Arial", 18));
		winAmountLabel.setAlignment(Pos.CENTER_LEFT);
		winAmountLabel.setPadding((new Insets(2, 10, 2, 10)));
		
		winAmount.setFont(Font.font("Arial", 18));
		winAmount.setAlignment(Pos.CENTER_RIGHT);
		winAmount.setPadding((new Insets(2, 10, 2, 10)));
	}

	private void createScoreBoard() {
		for(int i=0; i<scoreboard.length; i++) {
			scoreboard[i] = new GridPane();
		}
		//Set win amount text
		scoreboard[0].add(winAmountLabel, 0, 0);
		scoreboard[0].add(winAmount, 1, 0);
		
		//Set bank text
		scoreboard[1].add(bankLabel, 0, 0);
		scoreboard[1].add(bankAmount, 1, 0);
		
		GridPane.setFillWidth(winAmountLabel, true);
		winAmountLabel.setPrefWidth(150);

		GridPane.setFillWidth(bankLabel, true);
		bankLabel.setPrefWidth(150);		
	}

	private void addToVBox() {
		for(int i=0; i<scoreboard.length; i++) {
			this.getChildren().add(scoreboard[i]);
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
			this.setPrefWidth(280);
			this.setPrefHeight(80);
			this.setMaxHeight(USE_PREF_SIZE);
		}
		
		this.setAlignment(Pos.CENTER);
	}
	
	private String formattedNumber(int number) {
		DecimalFormat formatter = new DecimalFormat("#,###");		
		return formatter.format(number);
	}	
}
