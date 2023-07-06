package com.example.enigma;

import com.example.enigma.Model.FileManager;
import com.example.enigma.Model.IChangable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class ModifyFiles {

    private final FileManager csv;
    private final String path;

    public ModifyFiles(String path){
        csv = new FileManager(path);
        this.path = path;
    }

    public List<CSVRecord> readRecords() {
        try {
            CSVParser csvParser = csv.readFromFile();

            List<CSVRecord> records = csvParser.getRecords();

            csvParser.close();

            return records;

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }

        return null;
    }

    public void modifyRecords(List<CSVRecord> records, IChangable changeRecord){
        try {
            changeRecord.modify(records);

            File existingFile = new File(path);
            boolean deleted = existingFile.delete();

            CSVPrinter csvPrinter = csv.writeToFile();

            if (deleted){
                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }
            } else {
                System.out.println("Not deleted!");
            }

            csvPrinter.flush();
            csvPrinter.close();

        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void newRecord(List<CSVRecord> records, String[] newValues, String userID){
        try {
            for (int i = 0; i < records.size(); i++) {
                CSVRecord record = records.get(i);
                if (record.get(0).equals(userID)) {
                    CSVRecord newRecord = CSVFormat.DEFAULT
                            .parse(new StringReader(String.join(",", newValues)))
                            .iterator()
                            .next();

                    records.set(i, newRecord);
                }
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
