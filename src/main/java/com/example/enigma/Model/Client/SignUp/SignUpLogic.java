package com.example.enigma.Model.Client.SignUp;

import java.io.IOException;

import com.example.enigma.Model.FileManager;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class SignUpLogic {

    private final FileManager csvUser;
    private final FileManager csvRememberUSer;
    private final FileManager csvTrophy;
    private boolean userExists;

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

    private void addNewUser(int id, String userName, String passWord, String secretCode){
        try (CSVPrinter csvPrinter = csvUser.writeToFile()){

            csvPrinter.printRecord(String.valueOf(id), userName, passWord, secretCode);

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    private void remember(int id){
        try (CSVPrinter csvPrinter = csvRememberUSer.writeToFile()){

            String rememberUser = "false";
            csvPrinter.printRecord(String.valueOf(id), rememberUser);

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    private void initialTrophy(int id){
        try (CSVPrinter csvPrinter = csvTrophy.writeToFile()){

            csvPrinter.printRecord(String.valueOf(id), "0", "0", "0");

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

}
