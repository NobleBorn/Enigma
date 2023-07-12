package com.example.enigma.Model.Encryption.Shift;

import java.util.HashMap;

/**
 * The ShiftCipher class is a concrete implementation of the ShiftParent class.
 * It performs a shift cipher algorithm using a specified cipher key.
 * @author Mojtaba Alizade
 */
public class ShiftCipher extends ShiftParent {

    private final HashMap<Character, Character> keys = new HashMap<>();
    private final String cipherText;

    /**
     * Constructs a ShiftCipher object with the given cipher text and cipher key.
     *
     * @param cipherText The text to be ciphered.
     * @param cipherKey  The cipher key used for the shift cipher algorithm.
     */
    public ShiftCipher(String cipherText, String cipherKey){
        this.cipherText = cipherText;
        switchUp(cipherKey);
    }


    /**
     * Switches up the cipher key by rearranging the characters based on a specific pattern.
     *
     * @param key The current cipher key.
     */
    @Override
    public void switchUp(String key) {
        String lower = key.toLowerCase();
        String upper = key.toUpperCase();
        String secretKey = lower + upper;
        char[] keyValues = secretKey.toCharArray();
        keys.put(keyValues[0], keyValues[5]);
        keys.put(keyValues[1], keyValues[3]);
        keys.put(keyValues[2], keyValues[4]);
        keys.put(keyValues[3], keyValues[2]);
        keys.put(keyValues[4], keyValues[1]);
        keys.put(keyValues[5], keyValues[0]);
    }

    /**
     * Performs the shift cipher operation on the cipher text using the current cipher key.
     *
     * @return The ciphered text.
     */
    @Override
    public String shiftCipher() {
        char[] shiftChar = cipherText.toCharArray();

        for (int i = 0; i < shiftChar.length; i++) {
            if (keys.containsKey(shiftChar[i])){
                shiftChar[i] = keys.get(shiftChar[i]);
            }
        }

        return String.valueOf(shiftChar);
    }
}
