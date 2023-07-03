package com.example.enigma.Model;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private final static User instance = new User();
    private int id;
    private String name;
    private String secretCode;
    private String password;
    private final FileManager csvUser;
    private final FileManager csvKeys;
    private HashMap<String, String> userKeys = new HashMap<>();

    private User(){
        csvUser = new FileManager("src/main/resources/com/example/users.csv");
        csvKeys = new FileManager("src/main/resources/com/example/keys.csv");
    }

    public static User getInstance(){
        return instance;
    }

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

    public int getUserId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, String> getUserKeys(){
        return userKeys;
    }

    public void newMap(){
        userKeys = new HashMap<>();
    }
}
