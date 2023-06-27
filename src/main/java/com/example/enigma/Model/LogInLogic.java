package com.example.enigma.Model;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;

public class LogInLogic {

    private final FileManager csv;
    private final boolean authorizedUser;

    public LogInLogic(String userName, String passWord){
        csv = new FileManager("src/main/resources/com/example/users.csv");
        boolean userNameExists = checkUserName(userName);
        boolean correctPassWord = false;

        if (userNameExists){
            correctPassWord = checkPassWord(userName, passWord);
        }

        authorizedUser = correctPassWord;
    }

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

    public boolean isAuthorizedUser() {
        return authorizedUser;
    }
}
