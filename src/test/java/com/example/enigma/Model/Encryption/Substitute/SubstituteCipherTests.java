package com.example.enigma.Model.Encryption.Substitute;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubstituteCipherTests {

    HelperMethods helperMethods = new HelperMethods();
    private final String testKeysCsvPath = "src/test/resources/test_keys.csv";

    @Test
    void substituteCipher_SubstituteHalfCode(){
        SubstituteParent substitute = new SubstituteCipher("AbC", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(1);

        String expected = "NoP";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteHalfCodeWithKEy(){
        SubstituteParent substitute = new SubstituteCipher("AbC", "AbO",
                testKeysCsvPath);

        substitute.setCodeNumber(1);

        String expected = "ΣαP";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteInvertCode(){
        SubstituteParent substitute = new SubstituteCipher("AbC", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected = "ZyX";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);

        SubstituteParent substitute2 = new SubstituteCipher("xyz", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected2 = "cba";
        String actual2 = substitute.substitute(substitute2.getText());

        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void substituteCipher_SubstituteInvertCodeWithKEy(){
        SubstituteParent substitute = new SubstituteCipher("AbC", "AkO",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected = "ΣyX";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteStepCode(){
        SubstituteParent substitute1 = new SubstituteCipher("AbC", "Hlo",
                testKeysCsvPath);

        substitute1.setCodeNumber(3);

        String expected1 = "BcD";
        String actual1 = substitute1.substitute(substitute1.getText());

        Assertions.assertEquals(expected1, actual1);

        SubstituteParent substitute2 = new SubstituteCipher("xYz", "Hlo",
                testKeysCsvPath);

        substitute2.setCodeNumber(3);

        String expected2 = "yZa";
        String actual2 = substitute2.substitute(substitute2.getText());

        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void substituteCipher_SubstituteStepCodeWithKEy(){
        SubstituteParent substitute = new SubstituteCipher("AbC", "bCA",
                testKeysCsvPath);

        substitute.setCodeNumber(3);

        String expected = "βΣα";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }


    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testKeysCsvPath);
    }
}
