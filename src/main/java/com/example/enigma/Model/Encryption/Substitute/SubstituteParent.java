package com.example.enigma.Model.Encryption.Substitute;

import com.example.enigma.Model.FileManager;
import java.util.HashMap;

/**
 * The SubstituteParent class is a parent class for substitution ciphers.
 * It provides common functionality and attributes for different types of substitution ciphers.
 */
public class SubstituteParent {

    private final HashMap<Character, Character> codeHalf = new HashMap<>();
    private final HashMap<Character, Character> codeInvert = new HashMap<>();
    private final HashMap<Character, Character> codeStep = new HashMap<>();
    private final HashMap<Character, Character> codeKey = new HashMap<>();
    private int codeNumber;
    private final String Text;
    private final String Key;
    private final FileManager csv;
    private boolean found = false;

    /**
     * Constructs a SubstituteParent object with the given text and key.
     *
     * @param Text The text to be substituted.
     * @param Key  The substitution key used for the cipher.
     */
    protected SubstituteParent(String Text, String Key){
        this.Text = Text;
        this.Key = Key;
        csv = new FileManager("src/main/resources/com/example/keys.csv");
    }

    /**
     * Returns the mapping for the half substitution.
     *
     * @return The mapping for the half substitution.
     */
    protected HashMap<Character, Character> getCodeHalf() {
        return codeHalf;
    }

    /**
     * Returns the FileManager instance for accessing the CSV file.
     *
     * @return The FileManager instance.
     */
    protected FileManager getCsv() {
        return csv;
    }

    /**
     * Returns the mapping for the inverted substitution.
     *
     * @return The mapping for the inverted substitution.
     */
    protected HashMap<Character, Character> getCodeInvert() {
        return codeInvert;
    }

    /**
     * Returns the mapping for the key substitution.
     *
     * @return The mapping for the key substitution.
     */
    protected HashMap<Character, Character> getCodeKey() {
        return codeKey;
    }

    /**
     * Returns the mapping for the step substitution.
     *
     * @return The mapping for the step substitution.
     */
    protected HashMap<Character, Character> getCodeStep() {
        return codeStep;
    }

    /**
     * Sets the code number for selecting the appropriate substitution mapping.
     *
     * @param codeNumber The code number.
     */
    protected void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    /**
     * Returns the code number for the substitution mapping.
     *
     * @return The code number.
     */
    protected int getCodeNumber() {
        return codeNumber;
    }

    /**
     * Performs substitution on the given code using the selected substitution mapping.
     *
     * @param code The code to be substituted.
     * @return The substituted code.
     */
    protected String substitute(String code) {
        char[] substituteChar = code.toCharArray();

        HashMap<Character, Character> codeMap = switch (codeNumber) {
            case 1 -> codeHalf;
            case 2 -> codeInvert;
            default -> codeStep;
        };

        for (int i = 0; i < substituteChar.length; i++) {
            char currentChar = substituteChar[i];

            if (codeKey.containsKey(currentChar)) {
                char substitutedChar = codeKey.get(currentChar);
                substituteChar[i] = substitutedChar;
            } else if (codeMap.containsKey(currentChar)) {
                char substitutedChar = codeMap.get(currentChar);
                substituteChar[i] = substitutedChar;
            } else if (codeMap.containsValue(currentChar)) {
                for (char key : codeMap.keySet()) {
                    if (codeMap.get(key) == currentChar) {
                        substituteChar[i] = key;
                    }
                }
            }
        }

        return String.valueOf(substituteChar);
    }

    /**
     * Returns the original text to be substituted.
     *
     * @return The original text.
     */
    protected String getText() {
        return Text;
    }

    /**
     * Returns the substitution key used for the cipher.
     *
     * @return The substitution key.
     */
    protected String getKey() {
        return Key;
    }

    /**
     * Sets the found flag indicating if a match was found.
     */
    protected void setFound() {
        this.found = true;
    }

    /**
     * Returns the found flag indicating if a match was found.
     *
     * @return The found flag.
     */
    protected boolean isFound() {
        return found;
    }
}
