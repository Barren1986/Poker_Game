package gameoutput;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import player.Player;

public class GameFile {

    public static void writeCSVData(String filename, Player player, int winAmount) {
        // Path to the files directory
    	File file = new File("files/");

    	try (
            // Create PrintWriter and FileOutputStream
            FileOutputStream fileOutputStream = new FileOutputStream(file + filename, true);
            PrintWriter writer = new PrintWriter(fileOutputStream, true)) {

            // Output header
            writer.println("Player ID,Player Name,Hand,Hand Descr,Win Amount,Bank");

            // Output player data
            writer.printf(player.getId() + "," +
                           player.getName() + "," +
                           player.getHand() + "," +
                           player.getHand().getHandDescr() + "," +
                           winAmount + "," +
                           player.getBank());

            // Close the PrintWriter
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Write a method writeBinaryData that uses DataOutputStream to append data fed it to
    public static void writeBinaryData(String fileName, Player player, int winAmount) {
    	File file = new File("files/");
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file + fileName, true))) {

            // Write data to the file
            outputStream.writeUTF(player.getId());
            outputStream.writeUTF(player.getName());
            outputStream.writeUTF(player.getHand().getHandDescr());
            outputStream.writeInt(winAmount);
            outputStream.writeInt(player.getBank());

            // Close the PrintWriter
            outputStream.close();

        } catch (IOException ex) {
            System.out.println("Error Writing Data: ");
        }
    }
}
