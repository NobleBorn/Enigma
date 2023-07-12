package com.example.enigma.Model.Client;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class SignUpTests {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private final String testRememberUserCsvPath = "src/test/resources/test_rememberUser.csv";
    private final String testTrophyCsvPath = "src/test/resources/test_trophy.csv";
    private SignUpLogic validUser;
    private SignUpLogic existingUser;
    private HelperMethods helperMethods;

    @BeforeEach
    void setup() {
        String[] filePaths = {testUserCsvPath, testRememberUserCsvPath, testTrophyCsvPath};
        validUser = new SignUpLogic("John", "password", "Beartic", filePaths);
        existingUser = new SignUpLogic("Patrick", "SpongeBob", "Aron", filePaths);
        helperMethods = new HelperMethods();

        // Create empty test CSV files
        helperMethods.createEmptyCsvFile(testUserCsvPath);
        helperMethods.createEmptyCsvFile(testRememberUserCsvPath);
        helperMethods.createEmptyCsvFile(testTrophyCsvPath);
    }

    @Test
    void signUpLogic_SuccessfulSignUp() {
        // Arrange
        String userName = "John";
        String passWord = "password";
        String secretCode = "Beartic";

        // Act
        Assertions.assertTrue(validUser.addUser());

        // Assert
        // Check if the user CSV file contains the new user record
        String[] expectedUserRecord = { "1", userName, passWord, secretCode };
        String actualUserRecord = helperMethods.readCsvRecord(testUserCsvPath);
        Assertions.assertEquals(String.join(",", expectedUserRecord), actualUserRecord);

        // Check if the rememberUser CSV file contains the new user record
        String[] expectedRememberUserRecord = { "1", "false" };
        String actualRememberUserRecord = helperMethods.readCsvRecord(testRememberUserCsvPath);
        Assertions.assertEquals(String.join(",", expectedRememberUserRecord), actualRememberUserRecord);

        // Check if the trophy CSV file contains the new user record
        String[] expectedTrophyRecord = { "1", "0", "0", "0" };
        String actualTrophyRecord = helperMethods.readCsvRecord(testTrophyCsvPath);
        Assertions.assertEquals(String.join(",", expectedTrophyRecord), actualTrophyRecord);
    }

    @Test
    void signUpLogic_UserAlreadyExists_ThrowsIOException() {
        // Arrange
        String id = "1";
        String userName = "Patrick";
        String passWord = "SpongeBob";
        String secretCode = "Aron";
        String[] values = {id, userName, passWord, secretCode};

        // Create a test user record in the user CSV file
        helperMethods.writeCsvRecord(testUserCsvPath, values);

        // Act and Assert
        Assertions.assertFalse(existingUser.addUser());
    }


    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
        helperMethods.deleteFiles(testRememberUserCsvPath);
        helperMethods.deleteFiles(testTrophyCsvPath);
    }
}
