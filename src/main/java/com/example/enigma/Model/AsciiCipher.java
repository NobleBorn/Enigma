package com.example.enigma.Model;


public class AsciiCipher {

    private final String cipherText;
    private final String cipherKey;

    public AsciiCipher(String cipherText, String cipherKey){
        this.cipherText = cipherText;
        this.cipherKey = cipherKey;
    }

    public String ascii(){

        char[] asciiChar = cipherText.toCharArray();
        for (int i = 0; i < asciiChar.length; i++){
            int charValue = asciiChar[i];

            if ((i % 2) == 0){
                charValue += 5;
            }
            else {
                charValue -= 5;
            }

            charValue = ((charValue - 33) % 94 + 94) % 94 + 33;
            asciiChar[i] = (char) charValue;
        }


        return String.valueOf(asciiChar);
    }


}
