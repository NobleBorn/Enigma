package com.example.enigma.Model.Client.LogIn;

import com.example.enigma.Model.FileManager;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

/**
 * The LogInLogic class handles the logic for user login authentication.
 * It checks the provided username and password against a CSV file containing user credentials.
 * The class provides methods to perform the login check and retrieve the authorization status.
 */
public class LogInLogic {

    private final FileManager csv;
    private final boolean authorizedUser;

    /**
     * Constructs a LogInLogic object with the provided username and password.
     *
     * @param userName The username entered by the user.
     * @param passWord The password entered by the user.
     */
    public LogInLogic(String userName, String passWord){
        csv = new FileManager("src/main/resources/com/example/enigma/users.csv");
        boolean userNameExists = checkUserName(userName);
        boolean correctPassWord = false;

        if (userNameExists){
            correctPassWord = checkPassWord(userName, passWord);
        }

        authorizedUser = correctPassWord;
    }

    /**
     * Checks if the provided username exists in the user CSV file.
     *
     * @param userName The username to be checked.
     * @return true if the username exists, false otherwise.
     */
    private boolean checkUserName(String userName){
        boolean exists = false;

        try (CSVParser csvParser = csv.readFromFile()) {
            for (CSVRecord record : csvParser) {
                if (record.get(1).equals(userName)) {
                    exists = true;
                    break;
                }

            }

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return exists;
    }

    /**
     * Checks if the provided password matches the password associated with the given username.
     *
     * @param userName The username for password verification.
     * @param passWord The password to be checked.
     * @return true if the password is correct, false otherwise.
     */
    private boolean checkPassWord(String userName, String passWord){
        boolean correct = false;
        try (CSVParser csvParser = csv.readFromFile()) {
            for (CSVRecord record : csvParser) {
                if (record.get(1).equals(userName) && record.get(2).equals(passWord)) {
                    correct = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return correct;
    }

    /**
     * Checks if the user is authorized (valid username and password combination).
     *
     * @return true if the user is authorized, false otherwise.
     */
    public boolean isAuthorizedUser() {
        return authorizedUser;
    }
}
