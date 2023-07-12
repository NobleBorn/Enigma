package com.example.enigma.Model.Encryption;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CiphersTests {

    HelperMethods helperMethods = new HelperMethods();
    private final String testKeysCsvPath = "src/test/resources/test_keys.csv";

    @Test
    void successfulCipherAndDecipher(){
        String text = "Hello World!";
        String key = "HLW";

        Cipher cipher = new Cipher(text, key, testKeysCsvPath);
        String codedText = cipher.getCodedText();

        DeCipher deCipher = new DeCipher(codedText, key, testKeysCsvPath);

        Assertions.assertEquals(text, deCipher.getDeCodedText());
    }

    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testKeysCsvPath);
    }
}
