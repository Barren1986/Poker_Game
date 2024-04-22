package gameobjects;

import java.text.DecimalFormat;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import player.Player;

public class Wager extends VBox {
	
	private TextField wagerAmountText = new TextField();
	private Button btnAdd = new Button("+");
	private Button btnSubtract = new Button("-");
	
	private HBox wagerContainer = new HBox(10);
	private HBox buttonContainer = new HBox(20);
	
	private String borderColor = "green";
	
	private Player player;
	private int changeAmount;
	private int wagerAmount = 100;

	public Wager(Player player, int changeAmount) {
		this.player = player;
		this.changeAmount = changeAmount;
		
		buildWager();
	}

	public Wager(Player player, int changeAmount, String borderColor) {
		this.player = player;
		this.changeAmount = changeAmount;
		this.borderColor = borderColor;
		buildWager();
	}

	public int getWagerAmount() {
		return wagerAmount;
	}

	private void buildWager() {
		updateWagerText();		
		createWagerArea();
		createButtons();
		createButtonListeners();
		addToVBox();
		styleVBox();	
	}

	private void createWagerArea() {
		Label betLabel = new Label("Bet: ");
		betLabel.setFont(Font.font("Arial", 20));
		betLabel.setPadding((new Insets(0, 0, 0, 10)));

		wagerAmountText.setFont(Font.font("Arial", 20));
		wagerAmountText.setPrefColumnCount(5);
		wagerAmountText.setEditable(false);
		wagerAmountText.setPadding((new Insets(0, 0, 0, 0)));
		
		wagerContainer.getChildren().addAll(betLabel, wagerAmountText);
		wagerContainer.setPadding((new Insets(2, 0, 0, 50)));
		
	}

	private void createButtons() {
		//Style the buttons
		btnAdd.setStyle("-fx-font-size:18");
		btnSubtract.setStyle("-fx-font-size:18");

		buttonContainer.getChildren().addAll(btnAdd, btnSubtract);
		HBox.setMargin(btnAdd, new Insets(5, 0, 0, 100));
		HBox.setMargin(btnSubtract, new Insets(5, 0, 0, 0));
		
	}
	
	private void createButtonListeners() {
		// Assignment 3.2 Create listener for btnAdd
		btnAdd.setOnAction(event -> {
			changeWager(changeAmount);
		});
		
		// Assignment 3.2 Create listener for btnSubtract
		btnSubtract.setOnAction(event -> {
			changeWager(-changeAmount);
		});
	}
	
	private void changeWager(int change) {
		int newWager;
		
		if(this.wagerAmount + change <= 0) {
			newWager = 0;
		} else if (this.wagerAmount + change >= player.getBank()) {
			newWager = player.getBank();
		} else {
			newWager = this.wagerAmount + change;
		}
		
		this.wagerAmount = newWager;
		
		updateWagerText();
	}
	
	private void updateWagerText() {
		wagerAmountText.setText(formattedNumber(wagerAmount));		
	}

	private void addToVBox() {
		this.setPadding((new Insets(10, 0, 10, 0)));
		
		this.getChildren().addAll(wagerContainer, buttonContainer);
		
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
	}

	private String formattedNumber(int number) {
		DecimalFormat formatter = new DecimalFormat("#,###");		
		return formatter.format(number);
	}
	

}
