package com.example.enigma.Model.Encryption.Ascii;

/**
 * The AsciiCipher class extends the AsciiParent class and provides an implementation for performing
 * the ASCII cipher operation on a given cipher text.
 * @author Mojtaba Alizade
 */
public class AsciiCipher extends AsciiParent {

    private final String cipherText;

    /**
     * Constructs an AsciiCipher object with the specified cipher text.
     *
     * @param cipherText The text to be ciphered using ASCII cipher.
     */
    public AsciiCipher(String cipherText){
        this.cipherText = cipherText;
    }

    /**
     * Performs the ASCII cipher operation on the cipher text.
     *
     * @return The result of the ASCII cipher operation.
     */
    @Override
    public String asciiCipher() {
        char[] asciiChar = cipherText.toCharArray();
        for (int i = 0; i < asciiChar.length; i++){
            int charValue = asciiChar[i];

            if (charValue > 126) continue;

            if ((i % 2) == 0){
                charValue += 5;
            } else {
                charValue -= 5;
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

