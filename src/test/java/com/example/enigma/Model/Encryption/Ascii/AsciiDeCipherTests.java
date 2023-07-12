package com.example.enigma.Model.Encryption.Ascii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AsciiDeCipherTests {

    @Test
    void asciiCipher_EmptyString(){
        AsciiDeCipher cipher = new AsciiDeCipher("");
        String expected = "";
        Assertions.assertEquals(expected, cipher.asciiCipher());
    }

    @Test
    void asciiCipher_InRange(){
        AsciiDeCipher cipher = new AsciiDeCipher("M`qgt,7.90");
        String expected = "Hello12345";
        Assertions.assertEquals(expected, cipher.asciiCipher());
    }

    @Test
    void asciiCipher_CornerRanges(){
        AsciiDeCipher cornerCaseAtStart = new AsciiDeCipher("%{(");
        String expectedStart = " !#";
        Assertions.assertEquals(expectedStart, cornerCaseAtStart.asciiCipher());

        AsciiDeCipher cornerCaseAtEnd = new AsciiDeCipher("$x!");
        String expectedEnd = "~}{";
        Assertions.assertEquals(expectedEnd, cornerCaseAtEnd.asciiCipher());
    }
}
