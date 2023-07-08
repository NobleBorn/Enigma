package com.example.enigma.Model.Client;

import com.example.enigma.Model.FileManager;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


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

    private User(){
        csvUser = new FileManager("src/main/resources/com/example/enigma/users.csv");
        csvKeys = new FileManager("src/main/resources/com/example/enigma/keys.csv");
        csvRemember = new FileManager("src/main/resources/com/example/enigma/rememberUser.csv");
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

    public Map<String, String> getUserKeys(){
        return userKeys;
    }

    public void newMap(){
        userKeys = new LinkedHashMap<>();
    }

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
