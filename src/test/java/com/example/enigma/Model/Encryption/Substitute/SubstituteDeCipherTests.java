package com.example.enigma.Model.Encryption.Substitute;

import com.example.enigma.Model.HelperMethods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubstituteDeCipherTests {

    HelperMethods helperMethods = new HelperMethods();
    private final String testKeysCsvPath = "src/test/resources/test_keys.csv";

    @Test
    void substituteCipher_SubstituteHalfCode(){
        SubstituteParent substitute = new SubstituteDeCipher("NoP", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(1);

        String expected = "AbC";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteHalfCodeWithKEy(){
        SubstituteParent substitute = new SubstituteDeCipher("ΣαP", "AbO",
                testKeysCsvPath);

        substitute.setCodeNumber(1);

        String expected = "AbC";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteInvertCode(){
        SubstituteParent substitute = new SubstituteDeCipher("ZyX", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected = "AbC";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);

        SubstituteParent substitute2 = new SubstituteDeCipher("cba", "Hlo",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected2 = "xyz";
        String actual2 = substitute.substitute(substitute2.getText());

        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void substituteCipher_SubstituteInvertCodeWithKEy(){
        SubstituteParent substitute = new SubstituteDeCipher("ΣyX", "AkO",
                testKeysCsvPath);

        substitute.setCodeNumber(2);

        String expected = "AbC";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void substituteCipher_SubstituteStepCode(){
        SubstituteParent substitute1 = new SubstituteDeCipher("BcD", "Hlo",
                testKeysCsvPath);

        substitute1.setCodeNumber(3);

        String expected1 = "AbC";
        String actual1 = substitute1.substitute(substitute1.getText());

        Assertions.assertEquals(expected1, actual1);

        SubstituteParent substitute2 = new SubstituteDeCipher("yZa", "Hlo",
                testKeysCsvPath);

        substitute2.setCodeNumber(3);

        String expected2 = "xYz";
        String actual2 = substitute2.substitute(substitute2.getText());

        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void substituteCipher_SubstituteStepCodeWithKEy(){
        SubstituteParent substitute = new SubstituteDeCipher("βΣα", "bCA",
                testKeysCsvPath);

        substitute.setCodeNumber(3);

        String expected = "AbC";
        String actual = substitute.substitute(substitute.getText());

        Assertions.assertEquals(expected, actual);
    }


    @AfterEach
    void cleanUp(){
        helperMethods.deleteFiles(testKeysCsvPath);
    }
}
