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

    public SignUpLogic(String userName, String passWord){

        csv = new FileManager(String.valueOf(getClass().getResourceAsStream("users.csv")));
        int id = userID();
        addNewUser(id, userName, passWord);
    }

    private int userID(){
        int id = 0;

        try (CSVParser csvParser = csv.readFromFile()) {

            for (CSVRecord record : csvParser) {
                id++;
            }

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
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

}
