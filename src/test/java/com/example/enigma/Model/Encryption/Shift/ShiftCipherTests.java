package com.example.enigma.Model.Encryption.Shift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShiftCipherTests {

    @Test
    void shiftCipher_EmptyCipherText(){
        ShiftCipher cipher = new ShiftCipher("", "abc");
        String expected = "";
        Assertions.assertEquals(expected, cipher.shiftCipher());
    }

    @Test
    void shiftCipher_SuccessfulShifting(){
        ShiftCipher cipher = new ShiftCipher("ABCabc", "abc");
        String expected = "cbaCAB";
        Assertions.assertEquals(expected, cipher.shiftCipher());
    }
}
