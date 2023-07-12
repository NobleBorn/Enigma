package com.example.enigma.Model.Encryption.Ascii;

/**
 * The AsciiDeCipher class extends the AsciiParent class and provides an implementation for deciphering
 * a given cipher text using ASCII cipher.
 * @author Mojtaba Alizade
 */
public class AsciiDeCipher extends AsciiParent {

    private final String deCipherText;

    /**
     * Constructs an AsciiDeCipher object with the specified cipher text.
     *
     * @param deCipherText The cipher text to be deciphered using ASCII cipher.
     */
    public AsciiDeCipher(String deCipherText){
        this.deCipherText = deCipherText;
    }

    /**
     * Deciphers the cipher text using the ASCII cipher algorithm.
     *
     * @return The deciphered text.
     */
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

