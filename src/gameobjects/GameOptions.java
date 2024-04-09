package gameobjects;

import game.DealerArea;
import game.PlayerArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameOptions {
    
    // Attributes
    private VBox gameOptions = new VBox(	);    // VBox to hold the game options
    private ToggleGroup toggleGroup;
    
    // Create RadioButtons and create nodes to add
    private RadioButton rbRed = new RadioButton("Red");
    private RadioButton rbBlue = new RadioButton("Blue");
    private ComboBox<Integer> cbDrawCount;
    
    private Label lblDrawCounts;
    private Text headerText = new Text("Game Options");;
    private PlayerArea playerArea;
    private DealerArea dealerArea;
    
 // Constructor
    public GameOptions(PlayerArea playerArea, DealerArea dealerArea) {
        // Assign parameters to attributes
        this.playerArea = playerArea;
        this.dealerArea = dealerArea;
        
        // Instantiate VBox container
        //gameOptions = new VBox(10); // Use class attribute instead of local variable
        gameOptions.setSpacing(10);
        gameOptions.setPadding(new Insets(10));
        gameOptions.setAlignment(Pos.CENTER_LEFT); // Align VBox to the left  
        
        // Methods called by the Constructor
        createComboBox();
        createCardBackOptions();
        
     // Add nodes to the GameOptions VBox
        gameOptions.getChildren().addAll(headerText, rbRed, rbBlue, lblDrawCounts, cbDrawCount);

        
    }




    // Getter for accessing the gameOptions VBox - it needs to get something and return it to view.
	public VBox getGameOptions() {
		return gameOptions;
	}

	private void createCardBackOptions() {
        // Instantiate ToggleGroup for radio buttons
        toggleGroup = new ToggleGroup();

        // Add radio buttons to the container and toggle group
        rbRed.setToggleGroup(toggleGroup);        
        rbBlue.setToggleGroup(toggleGroup);
        
        // Each listener calls DealerArea(setCardBack) method and sends the appropriate color string
        rbRed.setOnAction(e -> dealerArea.setCardBack("red"));
        rbBlue.setOnAction(e -> dealerArea.setCardBack("blue"));

        // Set the default radio button to match the default card back color (Red)
        rbRed.setSelected(true);
        
        
    }

	// Method to create combo box for draw amount
	private void createComboBox() {
	    // Populating combo box
	    cbDrawCount = new ComboBox<>();
	    cbDrawCount.getItems().addAll(3, 4); // Default values for DrawPoker
	    
	    // Set the default value to match the default draw amount (4)
	    cbDrawCount.setValue(4);
	    
	    // Instantiate label for combo box
	    lblDrawCounts = new Label("Draw Amount", cbDrawCount);
	    
	    // Adding listener to combo box
	    cbDrawCount.setOnAction(event -> {
	        int selectedValue = cbDrawCount.getValue();
	        playerArea.setMaxSelect(selectedValue);
	    });
	}


}
