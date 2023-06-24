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
        char[] keyValues = cipherKey.toCharArray();
        keys.put(keyValues[0], keyValues[2]);
        keys.put(keyValues[2], keyValues[1]);
        keys.put(keyValues[1], keyValues[0]);
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