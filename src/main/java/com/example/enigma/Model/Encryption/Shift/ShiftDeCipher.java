package com.example.enigma.Model.Encryption.Shift;

import java.util.HashMap;

/**
 * The ShiftDeCipher class is a concrete implementation of the ShiftParent class.
 * It performs a reverse shift cipher algorithm using a specified decipher key.
 * @author Mojtaba Alizade
 */
public class ShiftDeCipher extends ShiftParent {

    private final HashMap<Character, Character> keys = new HashMap<>();
    private final String deCipherText;

    /**
     * Constructs a ShiftDeCipher object with the given decipher text and decipher key.
     *
     * @param deCipherText The text to be deciphered.
     * @param deCipherKey  The decipher key used for the reverse shift cipher algorithm.
     */
    public ShiftDeCipher(String deCipherText, String deCipherKey){
        this.deCipherText = deCipherText;
        switchUp(deCipherKey);
    }

    /**
     * Switches up the decipher key by rearranging the characters based on a specific pattern.
     *
     * @param key The current decipher key.
     */
    @Override
    public void switchUp(String key) {
        String lower = key.toLowerCase();
        String upper = key.toUpperCase();
        String secretKey = lower + upper;
        char[] keyValues = secretKey.toCharArray();
        keys.put(keyValues[5], keyValues[0]);
        keys.put(keyValues[3], keyValues[1]);
        keys.put(keyValues[4], keyValues[2]);
        keys.put(keyValues[2], keyValues[3]);
        keys.put(keyValues[1], keyValues[4]);
        keys.put(keyValues[0], keyValues[5]);
    }

    /**
     * Performs the reverse shift cipher operation on decipher text using the current decipher key.
     *
     * @return The deciphered text.
     */
    @Override
    public String shiftCipher() {
        char[] shiftChar = deCipherText.toCharArray();

        for (int i = 0; i < shiftChar.length; i++) {
            if (keys.containsKey(shiftChar[i])){
                shiftChar[i] = keys.get(shiftChar[i]);
            }
        }

        return String.valueOf(shiftChar);
    }
}
