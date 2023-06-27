package com.example.enigma.Model;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class User {

    private final static User instance = new User();
    private int id;
    private final FileManager csv;

    private User(){
        csv = new FileManager("src/main/resources/com/example/users.csv");
    }

    public static User getInstance(){
        return instance;
    }

    public void currentUser(String userName){
        try (CSVParser csvParser = csv.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(1).equals(userName)){
                    id = Integer.parseInt(record.get(0));
                    break;
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


}
