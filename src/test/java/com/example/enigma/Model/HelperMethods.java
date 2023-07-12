package com.example.enigma.Model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelperMethods {


    public void createEmptyCsvFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e){
            System.out.println("Error occurred while creating file: " + e.getMessage());
        }
    }

    // Helper method to write a CSV record to a file
    public void writeCsvRecord(String filePath, String[] record) {
        try (FileWriter writer = new FileWriter(filePath, true);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            if (record.length == 4) csvPrinter.printRecord(record[0], record[1], record[2], record[3]);
            else csvPrinter.printRecord(record[0], record[1]);
            System.out.println("CSV record written successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while writing the CSV file: " + e.getMessage());
        }
    }

    // Helper method to read a single CSV record from a file
    public String readCsvRecord(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Reader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser){
                int numberOfValues = record.values().length;
                for (int i = 0; i < numberOfValues; i++){
                    if (i < numberOfValues - 1) stringBuilder.append(record.get(i)).append(",");
                    else stringBuilder.append(record.get(i));
                }
            }

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }

        return stringBuilder.toString();
    }

    // Helper method to delete a CSV file
    public void deleteFiles(String filePath){
        File file = new File(filePath);
        boolean deleted = file.delete();
        if (!deleted){
            System.out.println("File did not delete!");
        }
    }
}
