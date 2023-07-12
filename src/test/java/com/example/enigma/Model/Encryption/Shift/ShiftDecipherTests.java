package com.example.enigma.Model.Encryption.Shift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShiftDecipherTests {

    @Test
    void shiftCipher_EmptyCipherText(){
        ShiftDeCipher cipher = new ShiftDeCipher("", "abc");
        String expected = "";
        Assertions.assertEquals(expected, cipher.shiftCipher());
    }

    @Test
    void shiftCipher_SuccessfulShifting(){
        ShiftDeCipher cipher = new ShiftDeCipher("cbaCAB", "abc");
        String expected = "ABCabc";
        Assertions.assertEquals(expected, cipher.shiftCipher());
    }
}
