package com.example.enigma.Model.Client;

import com.example.enigma.Model.Interfaces.IChangeable;
import com.example.enigma.Model.ModifyFiles;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * The SecretCode class represents the change of secret code functionality in the Enigma application.
 * It implements the IChangeable interface to handle modifications of CSV records.
 * @see IChangeable
 * @author Mojtaba Alizade
 */
public class SecretCode implements IChangeable {

    private final ModifyFiles modifyFiles;
    private final List<CSVRecord> records;
    private final User user;
    private final String newCode;

    /**
     * Constructs a SecretCode instance with the new secret code.
     *
     * @param newCode The new secret code.
     * @param filePath The path to the CSV file
     */
    public SecretCode(String newCode, String filePath){
        modifyFiles = new ModifyFiles(filePath);
        user = User.getInstance();
        user.currentUser(user.getName());
        records = modifyFiles.readRecords();
        this.newCode = newCode;
    }

    /**
     * Performs the secret code change by modifying the records and saving the changes.
     */
    public void secretCodeChange(){
        modifyFiles.modifyRecords(records, this);
    }

    /**
     * Implements the modify method from the IChangable interface.
     * Modifies the user's secret code in the records.
     *
     * @param records The list of CSV records.
     */
    @Override
    public void modify(List<CSVRecord> records) {
        String[] newValues = {String.valueOf(user.getUserId()), user.getName(), user.getPassword(), newCode};
        modifyFiles.newRecord(records, newValues, String.valueOf(user.getUserId()));
    }
}
