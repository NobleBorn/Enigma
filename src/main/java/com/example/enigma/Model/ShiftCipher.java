package com.example.enigma.Model;

import java.util.HashMap;

public class ShiftCipher extends ShiftParent{

    private final HashMap<Character, Character> keys = new HashMap<>();
    private final String cipherText;

    public ShiftCipher(String cipherText, String cipherKey){
        this.cipherText = cipherText;
        switchUp(cipherKey);
    }


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
