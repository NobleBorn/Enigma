package com.example.enigma.Model.Client.SignUp;

import java.io.IOException;

import com.example.enigma.Model.FileManager;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * The SignUpLogic class handles the logic for user sign-up process.
 * It interacts with CSV files to create new user records, remember user settings,
 * and initialize trophy records for new users.
 */
public class SignUpLogic {

    private final FileManager csvUser;
    private final FileManager csvRememberUSer;
    private final FileManager csvTrophy;
    private boolean userExists;

    /**
     * Constructs a SignUpLogic object with the provided user details.
     *
     * @param userName    The username entered by the user.
     * @param passWord    The password entered by the user.
     * @param secretCode  The secret code entered by the user.
     * @throws IOException If an error occurs while accessing the CSV files.
     */
    public SignUpLogic(String userName, String passWord, String secretCode) throws IOException {
        csvUser = new FileManager("src/main/resources/com/example/enigma/users.csv");
        csvRememberUSer = new FileManager("src/main/resources/com/example/enigma/rememberUser.csv");
        csvTrophy = new FileManager("src/main/resources/com/example/enigma/trophy.csv");

        userExists = false;
        int id = userID(userName);

        if (!userExists){
            addNewUser(id, userName, passWord, secretCode);
            remember(id);
            initialTrophy(id);
        } else {
            throw new IOException();
        }
    }

    /**
     * Checks if the username already exists in the user CSV file and returns the user ID.
     * If the username exists, sets the {@code userExists} flag to true.
     *
     * @param userName The username to be checked.
     * @return The user ID.
     */
    private int userID(String userName){
        int id = 0;

        try (CSVParser csvParser = csvUser.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(1).equals(userName)){
                    userExists = true;
                    break;
                }

            }

            id = (int) csvParser.getRecordNumber() + 1;

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return id;
    }

    /**
     * Adds a new user record to the user CSV file.
     *
     * @param id         The user ID.
     * @param userName   The username.
     * @param passWord   The password.
     * @param secretCode The secret code.
     */
    private void addNewUser(int id, String userName, String passWord, String secretCode){
        try (CSVPrinter csvPrinter = csvUser.writeToFile()){

            csvPrinter.printRecord(String.valueOf(id), userName, passWord, secretCode);

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Sets the default value to remember user's secret code for the new user in remember user CSV file.
     *
     * @param id The user ID.
     */
    private void remember(int id){
        try (CSVPrinter csvPrinter = csvRememberUSer.writeToFile()){

            String rememberUser = "false";
            csvPrinter.printRecord(String.valueOf(id), rememberUser);

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Initializes the trophy records for the new user in the trophy CSV file.
     *
     * @param id The user ID.
     */
    private void initialTrophy(int id){
        try (CSVPrinter csvPrinter = csvTrophy.writeToFile()){

            csvPrinter.printRecord(String.valueOf(id), "0", "0", "0");

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

}
