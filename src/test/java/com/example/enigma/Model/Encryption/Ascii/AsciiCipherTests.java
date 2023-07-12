package com.example.enigma.Model.Encryption.Ascii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AsciiCipherTests {

    @Test
    void asciiCipher_EmptyString(){
        AsciiCipher cipher = new AsciiCipher("");
        String expected = "";
        Assertions.assertEquals(expected, cipher.asciiCipher());
    }

    @Test
    void asciiCipher_InRange(){
        AsciiCipher cipher = new AsciiCipher("Hello12345");
        String expected = "M`qgt,7.90";
        Assertions.assertEquals(expected, cipher.asciiCipher());
    }

    @Test
    void asciiCipher_CornerRanges(){
        AsciiCipher cornerCaseAtStart = new AsciiCipher(" !#");
        String expectedStart = "%{(";
        Assertions.assertEquals(expectedStart, cornerCaseAtStart.asciiCipher());

        AsciiCipher cornerCaseAtEnd = new AsciiCipher("~}{");
        String expectedEnd = "$x!";
        Assertions.assertEquals(expectedEnd, cornerCaseAtEnd.asciiCipher());
    }
}
