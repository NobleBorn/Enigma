package com.example.enigma.Model;

import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.Interfaces.IChangeable;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

/**
 * The Trophy class represents the trophies earned by a user in the Enigma application.
 * It implements the IChangeable interface to modify the trophy records.
 * @see IChangeable
 */
public class Trophy implements IChangeable {

    private final FileManager csvTrophy;
    private final User user;
    private String trophy;
    private final ModifyFiles modifyFiles;
    private int operation;

    /**
     * Constructs a Trophy object.
     * It initializes the necessary file managers and retrieves the current user instance.
     */
    public Trophy() {
        modifyFiles = new ModifyFiles("src/main/resources/com/example/enigma/trophy.csv");
        csvTrophy = new FileManager("src/main/resources/com/example/enigma/trophy.csv");
        user = User.getInstance();
    }

    /**
     * Reads the trophy value from the trophy.csv file for the given trophy ID.
     *
     * @param trophyID The ID of the trophy to read (1 for cipher trophy, 2 for key trophy, 3 for decipher trophy).
     */
    private void readTrophy(int trophyID) {
        try (CSVParser csvParser = csvTrophy.readFromFile()) {
            for (CSVRecord record : csvParser) {
                if (record.get(0).equals(String.valueOf(user.getUserId()))) {
                    trophy = record.get(trophyID);
                    break;
                }
            }
            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Updates the trophy value based on the given operation ID.
     *
     * @param operationID The ID of the operation to update the trophy (1 for cipher, 2 for key, 3 for decipher).
     */
    public void updateTrophy(int operationID) {
        operation = operationID;
        List<CSVRecord> records = modifyFiles.readRecords();
        modifyFiles.modifyRecords(records, this);
    }

    /**
     * Retrieves the current cipher trophy value for the user.
     *
     * @return The cipher trophy value as an integer.
     */
    public int getCipherTrophy() {
        readTrophy(1);
        return Integer.parseInt(trophy);
    }

    /**
     * Retrieves the current key trophy value for the user.
     *
     * @return The key trophy value as an integer.
     */
    public int getKeyTrophy() {
        readTrophy(2);
        return Integer.parseInt(trophy);
    }

    /**
     * Retrieves the current decipher trophy value for the user.
     *
     * @return The decipher trophy value as an integer.
     */
    public int getDecipherTrophy() {
        readTrophy(3);
        return Integer.parseInt(trophy);
    }

    /**
     * Modifies the trophy records based on the operation and updates the CSV file.
     *
     * @param records The list of CSV records to modify.
     */
    @Override
    public void modify(List<CSVRecord> records) {
        int[] trophies = {getCipherTrophy(), getKeyTrophy(), getDecipherTrophy()};

        switch (operation) {
            case 1 -> {
                String[] newCipherVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0] + 1),
                        String.valueOf(trophies[1]), String.valueOf(trophies[2])};
                modifyFiles.newRecord(records, newCipherVal, String.valueOf(user.getUserId()));
            }
            case 2 -> {
                String[] newKeyVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0]),
                        String.valueOf(trophies[1] + 1), String.valueOf(trophies[2])};
                modifyFiles.newRecord(records, newKeyVal, String.valueOf(user.getUserId()));
            }
            case 3 -> {
                String[] newDecipherVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0]),
                        String.valueOf(trophies[1]), String.valueOf(trophies[2] + 1)};
                modifyFiles.newRecord(records, newDecipherVal, String.valueOf(user.getUserId()));
            }
            default -> System.out.println("No such operation");
        }
    }
}

