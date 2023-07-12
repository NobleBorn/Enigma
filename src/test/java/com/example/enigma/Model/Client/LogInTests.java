package com.example.enigma.Model.Client;


import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class LogInTests {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private HelperMethods helperMethods;

    @BeforeEach
    void setup() {
        helperMethods = new HelperMethods();

        // Create empty test CSV files
        helperMethods.createEmptyCsvFile(testUserCsvPath);
    }

    @Test
    void logInLogic_SuccessfulLogIn(){
        String id = "1";
        String userName = "John";
        String passWord = "password";
        String secretCode = "Aron";
        String[] values = {id, userName, passWord, secretCode};

        helperMethods.writeCsvRecord(testUserCsvPath, values);

        LogInLogic validUser = new LogInLogic("John", "password", testUserCsvPath);
        Assertions.assertTrue(validUser.isAuthorizedUser());
    }

    @Test
    void logInLogic_TwoSuccessfulLogIns(){
        String id1 = "1";
        String userName1 = "John";
        String passWord1 = "password";
        String secretCode1 = "Aron";
        String[] user1Values = {id1, userName1, passWord1, secretCode1};

        helperMethods.writeCsvRecord(testUserCsvPath, user1Values);

        String id2 = "2";
        String userName2 = "Peter";
        String passWord2 = "Nebula";
        String secretCode2 = "Meow";
        String[] user2Values = {id2, userName2, passWord2, secretCode2};

        helperMethods.writeCsvRecord(testUserCsvPath, user2Values);

        LogInLogic validUser1 = new LogInLogic("John", "password", testUserCsvPath);
        LogInLogic validUser2 = new LogInLogic("Peter", "Nebula", testUserCsvPath);


        Assertions.assertTrue(validUser1.isAuthorizedUser());
        Assertions.assertTrue(validUser2.isAuthorizedUser());
    }

    @Test
    void logInLogic_NonExistingUser(){
        LogInLogic nonExistingUser = new LogInLogic("Alice", "2323", testUserCsvPath);
        Assertions.assertFalse(nonExistingUser.isAuthorizedUser());
    }

    @Test
    void logInLogic_EmptyUserName(){
        String id = "1";
        String userName = "Alison";
        String passWord = "2020";
        String secretCode = "Aron";
        String[] values = {id, userName, passWord, secretCode};

        helperMethods.writeCsvRecord(testUserCsvPath, values);

        LogInLogic validUser = new LogInLogic("", "2020", testUserCsvPath);
        Assertions.assertFalse(validUser.isAuthorizedUser());
    }

    @Test
    void logInLogic_EmptyPassword(){
        String id = "1";
        String userName = "Alison";
        String passWord = "2020";
        String secretCode = "Aron";
        String[] values = {id, userName, passWord, secretCode};

        helperMethods.writeCsvRecord(testUserCsvPath, values);

        LogInLogic validUser = new LogInLogic("Alison", "", testUserCsvPath);
        Assertions.assertFalse(validUser.isAuthorizedUser());
    }

    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
    }
}
