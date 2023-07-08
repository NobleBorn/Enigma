package com.example.enigma.Model;

import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.Interfaces.IChangable;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

public class Trophy implements IChangable {

    private final FileManager csvTrophy;
    private final User user;
    private String trophy;
    private final ModifyFiles modifyFiles;
    private int operation;

    public Trophy(){
        modifyFiles = new ModifyFiles("src/main/resources/com/example/trophy.csv");
        csvTrophy = new FileManager("src/main/resources/com/example/trophy.csv");
        user = User.getInstance();
    }

    private void readTrophy(int trophyID){
        try (CSVParser csvParser = csvTrophy.readFromFile()) {
            for (CSVRecord record : csvParser){
                if (record.get(0).equals(String.valueOf(user.getUserId()))){
                    trophy = record.get(trophyID);
                    break;
                }
            }
            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    public void updateTrophy(int operationID){
        operation = operationID;
        List<CSVRecord> records = modifyFiles.readRecords();
        modifyFiles.modifyRecords(records, this);
    }

    public int getCipherTrophy(){
        readTrophy(1);
        return Integer.parseInt(trophy);
    }

    public int getKeyTrophy(){
        readTrophy(2);
        return Integer.parseInt(trophy);
    }

    public int getDecipherTrophy(){
        readTrophy(3);
        return Integer.parseInt(trophy);
    }

    @Override
    public void modify(List<CSVRecord> records) {
        int[] trophies = {getCipherTrophy(), getKeyTrophy(), getDecipherTrophy()};

        switch (operation) {
            case 1 -> {
                String[] newCipherVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0] + 1),
                        String.valueOf(trophies[1]), String.valueOf(trophies[2])};
                modifyFiles.newRecord(records, newCipherVal, String.valueOf(user.getUserId()));
            }
            case 2 -> {
                String[] newKeyVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0]),
                        String.valueOf(trophies[1] + 1), String.valueOf(trophies[2])};
                modifyFiles.newRecord(records, newKeyVal, String.valueOf(user.getUserId()));
            }
            case 3 -> {
                String[] newDecipherVal = {String.valueOf(user.getUserId()), String.valueOf(trophies[0]),
                        String.valueOf(trophies[1]), String.valueOf(trophies[2] + 1)};
                modifyFiles.newRecord(records, newDecipherVal, String.valueOf(user.getUserId()));
            }
            default -> System.out.println("No such operation");
        }
    }
}
