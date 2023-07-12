package com.example.enigma.Model.Encryption;

import com.example.enigma.Model.Encryption.Ascii.AsciiCipher;
import com.example.enigma.Model.Encryption.Shift.ShiftCipher;
import com.example.enigma.Model.Encryption.Substitute.SubstituteCipher;

/**
 * The Cipher class represents a multi-level cipher that combines different cipher techniques to encode a text.
 * It uses the ShiftCipher, SubstituteCipher, and AsciiCipher classes to perform the ciphering operations.
 * @see ShiftCipher
 * @see SubstituteCipher
 * @see AsciiCipher
 * @author Mojtaba Alizade
 */
public class Cipher {

    private final ShiftCipher shiftCipher;
    private final SubstituteCipher substituteCipher;
    private final AsciiCipher asciiCipher;

    /**
     * Constructs a Cipher object with the given cipher text and cipher key.
     *
     * @param cipherText The text to be ciphered.
     * @param cipherKey  The cipher key used for the cipher.
     * @param filePath The path to the CSV file
     */
    public Cipher(String cipherText, String cipherKey, String filePath) {
        shiftCipher = new ShiftCipher(cipherText, cipherKey);
        String textLevel1 = cipherLevel1();

        substituteCipher = new SubstituteCipher(textLevel1, cipherKey, filePath);
        String textLevel2 = cipherLevel2();

        asciiCipher = new AsciiCipher(textLevel2);
    }

    /**
     * Applies the level 1 ciphering operation using the ShiftCipher.
     *
     * @return The ciphered text after the level 1 operation.
     */
    private String cipherLevel1() {
        return shiftCipher.shiftCipher();
    }

    /**
     * Applies the level 2 ciphering operation using the SubstituteCipher.
     *
     * @return The ciphered text after the level 2 operation.
     */
    private String cipherLevel2() {
        return substituteCipher.substitution();
    }

    /**
     * Returns the final coded text after applying all the ciphering operations.
     *
     * @return The coded text.
     */
    public String getCodedText() {
        return asciiCipher.asciiCipher();
    }
}

