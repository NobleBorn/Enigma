package com.example.enigma.Model.Client;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SecretCodeTests {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private HelperMethods helperMethods;
    private final User user = User.getInstance();

    @BeforeEach
    void setUp(){
        helperMethods = new HelperMethods();

        String[] userInfo = {"1", "Adam", "2020", "oldCode"};
        helperMethods.writeCsvRecord(testUserCsvPath, userInfo);
    }

    @Test
    void secretCode_SuccessfullyChangeCode(){
        String[] filePaths = {testUserCsvPath, "", ""};
        user.init(filePaths);

        user.currentUser("Adam");

        Assertions.assertEquals("oldCode", user.getSecretCode());

        SecretCode secretCode = new SecretCode("newCode", testUserCsvPath);
        secretCode.secretCodeChange();

        user.currentUser("Adam");

        Assertions.assertEquals("newCode", user.getSecretCode());
    }

    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
    }
}
