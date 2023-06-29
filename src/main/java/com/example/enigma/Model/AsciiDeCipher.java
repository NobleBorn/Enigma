package com.example.enigma.Model;

public class AsciiDeCipher extends AsciiParent{

    private final String deCipherText;

    public AsciiDeCipher(String deCipherText){
        this.deCipherText = deCipherText;
    }

    @Override
    public String asciiCipher() {
        char[] asciiChar = deCipherText.toCharArray();

        for (int i = 0; i < asciiChar.length; i++){
            int charValue = asciiChar[i];

            if (charValue > 126) continue;

            if ((i % 2) == 0){
                charValue -= 5;
            } else {
                charValue += 5;
            }

            if (charValue < 32) {
                charValue += 95;
            } else if (charValue > 126) {
                charValue -= 95;
            }

            asciiChar[i] = (char) charValue;
        }

        return String.valueOf(asciiChar);
    }
}
