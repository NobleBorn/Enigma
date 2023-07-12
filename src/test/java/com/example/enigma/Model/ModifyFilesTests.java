package com.example.enigma.Model;

import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.Interfaces.IChangeable;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ModifyFilesTests implements IChangeable {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private HelperMethods helperMethods;
    private ModifyFiles modifyFiles;
    private final User user = User.getInstance();

    @BeforeEach
    void setUp(){
        helperMethods = new HelperMethods();

        String[] userInfo = {"1", "Adam", "2020", "Pikachu"};
        helperMethods.writeCsvRecord(testUserCsvPath, userInfo);

        String[] filePaths = {testUserCsvPath, "", ""};
        user.init(filePaths);
        user.currentUser("Adam");
    }

    @Test
    void modify_SuccessfullyModify(){
        Assertions.assertEquals("Adam", user.getName());
        Assertions.assertEquals("2020", user.getPassword());
        Assertions.assertEquals("Pikachu", user.getSecretCode());

        modifyFiles = new ModifyFiles(testUserCsvPath);
        List<CSVRecord> records = modifyFiles.readRecords();
        modifyFiles.modifyRecords(records, this);

        user.currentUser("Alison");

        Assertions.assertEquals("Alison", user.getName());
        Assertions.assertEquals("1010", user.getPassword());
        Assertions.assertEquals("Meow", user.getSecretCode());
    }

    @Test
    void modify_EmptyFilePath(){
        ModifyFiles modifyEmptyFiles = new ModifyFiles("");
        Assertions.assertTrue(modifyEmptyFiles.readRecords().isEmpty());
    }

    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
    }

    @Override
    public void modify(List<CSVRecord> records) {
        String[] newValues = {String.valueOf(user.getUserId()), "Alison", "1010", "Meow"};
        modifyFiles.newRecord(records, newValues, String.valueOf(user.getUserId()));
    }
}
