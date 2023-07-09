package com.example.enigma.Model.Client;

import com.example.enigma.Model.FileManager;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The User class represents a user in the Enigma application.
 * It provides access to user information, such as ID, name, secret code, password, and user keys.
 * The User class follows the singleton design pattern.
 */
public class User {

    private final static User instance = new User();
    private int id;
    private String name;
    private String secretCode;
    private String password;
    private final FileManager csvUser;
    private final FileManager csvKeys;
    private final FileManager csvRemember;
    private Map<String, String> userKeys = new LinkedHashMap<>();

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private User(){
        csvUser = new FileManager("src/main/resources/com/example/enigma/users.csv");
        csvKeys = new FileManager("src/main/resources/com/example/enigma/keys.csv");
        csvRemember = new FileManager("src/main/resources/com/example/enigma/rememberUser.csv");
    }

    /**
     * Returns the instance of the User class.
     *
     * @return The User instance.
     */
    public static User getInstance(){
        return instance;
    }

    /**
     * Sets the current user based on the provided username.
     * Retrieves the user information from the user CSV file.
     *
     * @param userName The username of the current user.
     */
    public void currentUser(String userName){
        try (CSVParser csvParser = csvUser.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(1).equals(userName)){
                    id = Integer.parseInt(record.get(0));
                    name = record.get(1);
                    password = record.get(2);
                    secretCode = record.get(3);
                    break;
                }
            }

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the user keys from the keys CSV file.
     * Populates the userKeys map with the key-value pairs.
     */
    public void keys(){
        try (CSVParser csvParser = csvKeys.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (Integer.parseInt(record.get(0)) == id){
                    userKeys.put(record.get(2), record.get(3));
                }
            }

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Returns the ID of the current user.
     *
     * @return The user ID.
     */
    public int getUserId(){
        return id;
    }

    /**
     * Returns the name of the current user.
     *
     * @return The user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the secret code of the current user.
     *
     * @return The user secret code.
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * Returns the password of the current user.
     *
     * @return The user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user keys as a map.
     *
     * @return The user keys map.
     */
    public Map<String, String> getUserKeys(){
        return userKeys;
    }

    /**
     * Clears the userKeys map.
     */
    public void newMap(){
        userKeys = new LinkedHashMap<>();
    }

    /**
     * Checks if the current user's secret code is remembered based on remember CSV file.
     *
     * @return {@code true} if the user is remembered, {@code false} otherwise.
     */
    public boolean isRemembered(){
        boolean rememberUser = false;
        try (CSVParser csvParser = csvRemember.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(0).equals(String.valueOf(id))){
                    if (record.get(1).equals("true")){
                        rememberUser = true;
                    }
                    break;
                }
            }

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return rememberUser;
    }
}
