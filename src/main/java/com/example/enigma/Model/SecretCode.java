package com.example.enigma.Model;

import com.example.enigma.ModifyFiles;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class SecretCode implements IChangable{

    private final ModifyFiles modifyFiles;
    private final List<CSVRecord> records;
    private final User user;
    private final String newCode;

    public SecretCode(String newCode){
        modifyFiles = new ModifyFiles("src/main/resources/com/example/users.csv");
        user = User.getInstance();
        user.currentUser(user.getName());
        records = modifyFiles.readRecords();
        this.newCode = newCode;
    }

    public void secretCodeChange(){
        modifyFiles.modifyRecords(records, this);
    }

    @Override
    public void modify(List<CSVRecord> records) {
        String[] newValues = {String.valueOf(user.getUserId()), user.getName(), user.getPassword(), newCode};
        modifyFiles.newRecord(records, newValues, String.valueOf(user.getUserId()));
    }
}
