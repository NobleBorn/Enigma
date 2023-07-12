package com.example.enigma.Model;

import com.example.enigma.Model.Client.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrophyTests {

    private final String testUserCsvPath = "src/test/resources/test_users.csv";
    private final String testTrophyCsvPath = "src/test/resources/test_trophy.csv";
    private final HelperMethods helperMethods = new HelperMethods();
    private final User user = User.getInstance();

    @BeforeEach
    void setUp(){
        String[] userInfo = {"1","Adam","1212","Pikachu"};
        helperMethods.writeCsvRecord(testUserCsvPath, userInfo);

        String[] filePaths = {testUserCsvPath, "", ""};
        user.init(filePaths);
        user.currentUser("Adam");
    }

    @Test
    void trophy_SuccessfullyGetInfo(){
        String[] trophyVal = {"1","10","6","0"};
        helperMethods.writeCsvRecord(testTrophyCsvPath, trophyVal);

        Trophy trophy = new Trophy(testTrophyCsvPath);

        Assertions.assertEquals(10, trophy.getCipherTrophy());
        Assertions.assertEquals(6, trophy.getKeyTrophy());
        Assertions.assertEquals(0, trophy.getDecipherTrophy());

    }

    @Test
    void trophy_SuccessfullyUpdateInfo(){
        String[] trophyVal = {"1","3","13","6"};
        helperMethods.writeCsvRecord(testTrophyCsvPath, trophyVal);

        Trophy trophy = new Trophy(testTrophyCsvPath);

        Assertions.assertEquals(3, trophy.getCipherTrophy());
        Assertions.assertEquals(13, trophy.getKeyTrophy());
        Assertions.assertEquals(6, trophy.getDecipherTrophy());

        trophy.updateTrophy(1);
        trophy.updateTrophy(2);
        trophy.updateTrophy(3);

        Assertions.assertEquals(4, trophy.getCipherTrophy());
        Assertions.assertEquals(14, trophy.getKeyTrophy());
        Assertions.assertEquals(7, trophy.getDecipherTrophy());

    }

    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testUserCsvPath);
        helperMethods.deleteFiles(testTrophyCsvPath);
    }
}
