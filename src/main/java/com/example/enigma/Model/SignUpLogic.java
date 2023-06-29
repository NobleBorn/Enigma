package com.example.enigma.Model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class SignUpLogic {

    private final FileManager csv;
    private boolean userExists;

    public SignUpLogic(String userName, String passWord) throws IOException {
        csv = new FileManager("src/main/resources/com/example/users.csv");
        userExists = false;
        int id = userID(userName);

        if (!userExists){
            addNewUser(id, userName, passWord);
        } else {
            throw new IOException();
        }
    }

    private int userID(String userName){
        int id = 0;

        try (CSVParser csvParser = csv.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(1).equals(userName)){
                    userExists = true;
                    break;
                }
            }

            id = csvParser.getRecords().size() + 1;

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return id;
    }

    private void addNewUser(int id, String userName, String passWord){
        try (CSVPrinter csvPrinter = csv.writeToFile()){

            // Add the new user to the csv
            csvPrinter.printRecord(String.valueOf(id), userName, passWord);

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

}
