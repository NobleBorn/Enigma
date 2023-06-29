package com.example.enigma.Model;

import java.util.HashMap;

public class ShiftDeCipher {

    private final HashMap<Character, Character> keys = new HashMap<>();
    private final String deCipherText;

    public ShiftDeCipher(String deCipherText, String deCipherKey){
        this.deCipherText = deCipherText;
        switchUp(deCipherKey);
    }

    private void switchUp(String deCipherKey) {
        String lower = deCipherKey.toLowerCase();
        String upper = deCipherKey.toUpperCase();
        String key = lower + upper;
        char[] keyValues = key.toCharArray();
        keys.put(keyValues[5], keyValues[0]);
        keys.put(keyValues[3], keyValues[1]);
        keys.put(keyValues[4], keyValues[2]);
        keys.put(keyValues[2], keyValues[3]);
        keys.put(keyValues[1], keyValues[4]);
        keys.put(keyValues[0], keyValues[5]);
    }

    public String shiftBack() {
        char[] shiftChar = deCipherText.toCharArray();

        for (int i = 0; i < shiftChar.length; i++) {
            if (keys.containsKey(shiftChar[i])){
                shiftChar[i] = keys.get(shiftChar[i]);
            }
        }

        return String.valueOf(shiftChar);
    }
}
