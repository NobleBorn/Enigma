package com.example.enigma.Model;

import com.example.enigma.Model.Interfaces.IChangeable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

/**
 * The ModifyFiles class provides methods to modify CSV files.
 * It allows reading records, modifying existing records, and adding new records.
 */
public class ModifyFiles {

    private final FileManager csv;
    private final String path;

    /**
     * Constructs a ModifyFiles object with the specified file path.
     *
     * @param path the path to the CSV file
     */
    public ModifyFiles(String path){
        csv = new FileManager(path);
        this.path = path;
    }

    /**
     * Reads the records from the CSV file.
     *
     * @return the list of CSV records
     */
    public List<CSVRecord> readRecords() {
        try {
            CSVParser csvParser = csv.readFromFile();
            List<CSVRecord> records = csvParser.getRecords();
            csvParser.close();
            return records;
        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Modifies the provided records using the given IChangeable object.
     *
     * @param records      the list of CSV records to be modified
     * @param changeRecord the IChangeable object responsible for modifying the records
     */
    public void modifyRecords(List<CSVRecord> records, IChangeable changeRecord){
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

    /**
     * Updates a specific record with new values.
     *
     * @param records   the list of CSV records
     * @param newValues the new values to update the record
     * @param userID    the ID of the record to be updated
     */
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
