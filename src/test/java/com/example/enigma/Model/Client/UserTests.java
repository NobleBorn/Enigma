package com.example.enigma.Model.Client;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.*;

import java.util.Map;

public class UserTests {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private final String testRememberUserCsvPath = "src/test/resources/test_rememberUser.csv";
    private final String testKeysCsvPath = "src/test/resources/test_keys.csv";

    private final User user = User.getInstance();
    private HelperMethods helperMethods;

    @BeforeEach
    void setup() {
        String[] filePaths = {testUserCsvPath, testRememberUserCsvPath, testKeysCsvPath};
        helperMethods = new HelperMethods();

        // Create empty test CSV files
        helperMethods.createEmptyCsvFile(testUserCsvPath);
        helperMethods.createEmptyCsvFile(testRememberUserCsvPath);
        helperMethods.createEmptyCsvFile(testKeysCsvPath);

        String[] userInfo = {"1", "Adam", "4444", "Aron"};
        helperMethods.writeCsvRecord(testUserCsvPath, userInfo);

        user.init(filePaths);
    }

    @Test
    void user_GetUserInfoSuccessfully(){
        user.currentUser("Adam");

        Assertions.assertEquals(1, user.getUserId());
        Assertions.assertEquals("Adam", user.getName());
        Assertions.assertEquals("4444", user.getPassword());
        Assertions.assertEquals("Aron", user.getSecretCode());
    }

    @Test
    void user_UserIsNotRemembered(){
        String[] rememberUser = {"1", "false"};
        helperMethods.writeCsvRecord(testRememberUserCsvPath, rememberUser);

        user.currentUser("Adam");
        Assertions.assertFalse(user.isRemembered());

    }

    @Test
    void user_UserIsRemembered(){
        String[] rememberUser = {"1", "true"};
        helperMethods.writeCsvRecord(testRememberUserCsvPath, rememberUser);

        user.currentUser("Adam");
        Assertions.assertTrue(user.isRemembered());

    }

    @Test
    void user_EmptyKeys(){
        user.currentUser("Adam");
        user.keys();

        boolean keys = user.getUserKeys().isEmpty();
        Assertions.assertTrue(keys);
    }

    @Test
    void user_NonEmptyKeys(){
        String[] userKeys = {"1", "2", "abc", "2023-07-10 16-01-40"};
        helperMethods.writeCsvRecord(testKeysCsvPath, userKeys);

        user.currentUser("Adam");
        user.keys();

        Map<String, String> keys = user.getUserKeys();
        Assertions.assertEquals(userKeys[3], keys.get(userKeys[2]));
    }

    @Test
    void user_EmptyTheKeys(){
        String[] userKeys = {"1", "2", "abc", "2023-07-10 16-01-40"};
        helperMethods.writeCsvRecord(testKeysCsvPath, userKeys);

        user.currentUser("Adam");
        user.keys();

        Map<String, String> keys = user.getUserKeys();
        Assertions.assertEquals(userKeys[3], keys.get(userKeys[2]));
        user.newMap();

        user.currentUser("Adam");

        boolean newKeys = user.getUserKeys().isEmpty();
        Assertions.assertTrue(newKeys);
    }


    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
        helperMethods.deleteFiles(testRememberUserCsvPath);
        helperMethods.deleteFiles(testKeysCsvPath);
    }

}
