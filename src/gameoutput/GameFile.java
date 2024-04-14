package gameoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import player.Player;

public class GameFile {

    public static void writeCSVData(String filename, Player player, int winAmount) {
        // Path to the files directory
    	File file = new File("files");

        try {
            // Create PrintWriter with FileWriter
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

            // Output header
            writer.println("Player ID,Player Name,Hand,Hand Descr,Win Amount,Bank");

            // Output player data
            writer.println(player.getId() + "," +
                           player.getName() + "," +
                           player.getHand() + "," +
                           player.getHand().getHandDescr() + "," +
                           winAmount + "," +
                           player.getBank());

            // Close the PrintWriter
            writer.close();

            System.out.println("Data written to " + filename + " successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
