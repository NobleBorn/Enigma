package com.example.enigma.Model.Encryption;

import com.example.enigma.Model.Encryption.Ascii.AsciiCipher;
import com.example.enigma.Model.Encryption.Ascii.AsciiDeCipher;
import com.example.enigma.Model.Encryption.Shift.ShiftCipher;
import com.example.enigma.Model.Encryption.Shift.ShiftDeCipher;
import com.example.enigma.Model.Encryption.Substitute.SubstituteCipher;
import com.example.enigma.Model.Encryption.Substitute.SubstituteDeCipher;

/**
 * The DeCipher class represents a multi-level decipher that reverses the ciphering operations performed by the Cipher class.
 * It uses the AsciiDeCipher, SubstituteDeCipher, and ShiftDeCipher classes to perform the deciphering operations.
 * @see ShiftCipher
 * @see SubstituteCipher
 * @see AsciiCipher
 */
public class DeCipher {

    private final ShiftDeCipher shiftDeCipher;
    private final SubstituteDeCipher substituteDeCipher;
    private final AsciiDeCipher asciiDeCipher;

    /**
     * Constructs a DeCipher object with the given decipher text and decipher key.
     *
     * @param deCipherText The text to be deciphered.
     * @param deCipherKey  The decipher key used for the decipher.
     */
    public DeCipher(String deCipherText, String deCipherKey){
        asciiDeCipher = new AsciiDeCipher(deCipherText);
        String deCipherLevel1 = deCipherLevel1();

        substituteDeCipher = new SubstituteDeCipher(deCipherLevel1, deCipherKey);
        String deCipherLevel2 = deCipherLevel2();

        shiftDeCipher = new ShiftDeCipher(deCipherLevel2, deCipherKey);
    }

    /**
     * Applies the level 1 deciphering operation using the AsciiDeCipher.
     *
     * @return The deciphered text after the level 1 operation.
     */
    private String deCipherLevel1(){
        return asciiDeCipher.asciiCipher();
    }

    /**
     * Applies the level 2 deciphering operation using the SubstituteDeCipher.
     *
     * @return The deciphered text after the level 2 operation.
     */
    private String deCipherLevel2(){
        return substituteDeCipher.deSubstitute();
    }

    /**
     * Returns the final deciphered text after applying all the deciphering operations.
     *
     * @return The deciphered text.
     */
    public String getDeCodedText(){
        return shiftDeCipher.shiftCipher();
    }
}

