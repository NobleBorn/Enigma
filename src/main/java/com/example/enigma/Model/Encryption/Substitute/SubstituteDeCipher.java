package com.example.enigma.Model.Encryption.Substitute;

import com.example.enigma.Model.Client.User;
import org.apache.commons.csv.CSVParser;
import java.io.IOException;

/**
 * The SubstituteDeCipher class is responsible for performing the deciphering operation using a substitution cipher.
 * It uses a provided decipher text and decipher key to reverse the substitution and obtain the original text.
 */
public class SubstituteDeCipher {

    private final SubstituteParent substituteDeCipher;
    private final User user;

    /**
     * Constructs a SubstituteDeCipher object with the given decipher text and decipher key.
     *
     * @param deCipherText The text to be deciphered.
     * @param deCipherKey  The decipher key used for the cipher.
     */
    public SubstituteDeCipher(String deCipherText, String deCipherKey) {
        substituteDeCipher = new SubstituteParent(deCipherText, deCipherKey);
        user = User.getInstance();
        populateCodes();
        getCodeNumber();
    }

    /**
     * Populates the different substitution codes required for deciphering.
     */
    private void populateCodes() {
        populateCodeHalf();
        populateCodeInvert();
        populateCodeStep();
        populateCodeKey();
    }

    /**
     * Retrieves the code number from the CSV file based on the user's ID and decipher key.
     * Sets the code number and the found flag if a match is found.
     */
    private void getCodeNumber() {
        int userId = user.getUserId();

        try (CSVParser csvParser = substituteDeCipher.getCsv().readFromFile()) {
            csvParser.forEach(record -> {
                if (Integer.parseInt(record.get(0)) == userId && record.get(2).equals(substituteDeCipher.getKey())) {
                    substituteDeCipher.setCodeNumber(Integer.parseInt(record.get(1)));
                    substituteDeCipher.setFound();
                }
            });

            if (!substituteDeCipher.isFound()) System.out.println("Not found");
            System.out.println("CSV file read successfully!");
        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Performs the deciphering operation using the substitution cipher.
     *
     * @return The deciphered text.
     */
    public String deSubstitute() {
        return substituteDeCipher.substitute(substituteDeCipher.getText());
    }

    /**
     * Populates the code half substitution mapping.
     */
    private void populateCodeHalf() {
        for (int i = 90; i >= 78; i--) {
            substituteDeCipher.getCodeHalf().put((char) i, (char) (i - 13));
        }

        for (int i = 122; i >= 110; i--) {
            substituteDeCipher.getCodeHalf().put((char) i, (char) (i - 13));
        }
    }

    /**
     * Populates the code invert substitution mapping.
     */
    private void populateCodeInvert() {
        int steps = 25;
        int positionKey = 91;

        while (steps >= 1) {
            positionKey--;
            substituteDeCipher.getCodeInvert().put((char) positionKey, (char) (positionKey - steps));
            steps -= 2;
        }

        int stepsMinor = 25;
        int positionKeyMinor = 123;

        while (stepsMinor >= 1) {
            positionKeyMinor--;
            substituteDeCipher.getCodeInvert().put((char) positionKeyMinor, (char) (positionKeyMinor - stepsMinor));
            stepsMinor -= 2;
        }
    }

    /**
     * Populates the code step substitution mapping.
     */
    private void populateCodeStep() {
        for (int i = 90; i >= 66; i--) {
            substituteDeCipher.getCodeStep().put((char) i, (char) (i - 1));
        }

        substituteDeCipher.getCodeStep().put((char) 65, (char) 90);

        for (int i = 122; i >= 98; i--) {
            substituteDeCipher.getCodeStep().put((char) i, (char) (i - 1));
        }

        substituteDeCipher.getCodeStep().put((char) 97, (char) 122);
    }

    /**
     * Populates the code key substitution mapping.
     */
    private void populateCodeKey() {
        char[] keyChar = substituteDeCipher.getKey().toCharArray();
        substituteDeCipher.getCodeKey().put('\u03A3', keyChar[0]);
        substituteDeCipher.getCodeKey().put('\u03B1', keyChar[1]);
        substituteDeCipher.getCodeKey().put('\u03B2', keyChar[2]);
    }
}

