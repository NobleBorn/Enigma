package com.example.enigma.Model;

import java.util.HashMap;

public class ShiftCipher {

    private final HashMap<Character, Character> keys = new HashMap<>();
    private final String cipherText;

    public ShiftCipher(String cipherText, String cipherKey){
        this.cipherText = cipherText;
        switchUp(cipherKey);
    }

    private void switchUp(String cipherKey) {
        String lower = cipherKey.toLowerCase();
        String upper = cipherKey.toUpperCase();
        String key = lower + upper;
        char[] keyValues = key.toCharArray();
        keys.put(keyValues[0], keyValues[5]);
        keys.put(keyValues[1], keyValues[3]);
        keys.put(keyValues[2], keyValues[4]);
        keys.put(keyValues[3], keyValues[2]);
        keys.put(keyValues[4], keyValues[1]);
        keys.put(keyValues[5], keyValues[0]);
    }

    public String shift() {
        char[] shiftChar = cipherText.toCharArray();

        for (int i = 0; i < shiftChar.length; i++) {
            if (keys.containsKey(shiftChar[i])){
                shiftChar[i] = keys.get(shiftChar[i]);
            }
        }

        return String.valueOf(shiftChar);
    }
}
