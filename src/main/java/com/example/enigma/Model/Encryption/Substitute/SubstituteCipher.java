package com.example.enigma.Model.Encryption.Substitute;

import com.example.enigma.Model.Client.User;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * The SubstituteCipher class is responsible for performing the ciphering operation using a substitution cipher.
 * It uses a provided cipher text and cipher key to substitute characters and obtain the encoded text.
 */
public class SubstituteCipher {

    private final SubstituteParent substituteCipher;
    private final User user;

    /**
     * Constructs a SubstituteCipher object with the given cipher text and cipher key.
     *
     * @param cipherText The text to be ciphered.
     * @param cipherKey  The cipher key used for the cipher.
     */
    public SubstituteCipher(String cipherText, String cipherKey) {
        substituteCipher = new SubstituteParent(cipherText, cipherKey);
        user = User.getInstance();
        populateCodes();
        Random random = new Random();
        int codeNumber = random.nextInt(3) + 1;
        substituteCipher.setCodeNumber(codeNumber);
        saveCodeNumber();
    }

    /**
     * Populates the different substitution codes required for ciphering.
     */
    private void populateCodes() {
        populateCodeHalf();
        populateCodeInvert();
        populateCodeStep();
        populateCodeKey();
    }

    /**
     * Performs the ciphering operation using the substitution cipher.
     *
     * @return The ciphered text.
     */
    public String substitute() {
        return substituteCipher.substitute(substituteCipher.getText());
    }

    /**
     * Saves the code number and current timestamp to the CSV file.
     */
    private void saveCodeNumber() {
        try (CSVPrinter csvPrinter = substituteCipher.getCsv().writeToFile()) {
            Instant timestamp = Instant.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ZonedDateTime zonedDateTime = timestamp.atZone(ZoneId.systemDefault());
            String formattedTimestamp = formatter.format(zonedDateTime);
            System.out.println(formattedTimestamp);

            csvPrinter.printRecord(String.valueOf(user.getUserId()),
                    String.valueOf(substituteCipher.getCodeNumber()), substituteCipher.getKey(), formattedTimestamp);
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Populates the code half substitution mapping.
     */
    private void populateCodeHalf() {
        for (int i = 65; i <= 77; i++) {
            substituteCipher.getCodeHalf().put((char) i, (char) (i + 13));
        }

        for (int i = 97; i <= 109; i++) {
            substituteCipher.getCodeHalf().put((char) i, (char) (i + 13));
        }
    }

    /**
     * Populates the code invert substitution mapping.
     */
    private void populateCodeInvert() {
        int steps = 25;
        int positionKey = 64;

        while (steps >= 1) {
            positionKey++;
            substituteCipher.getCodeInvert().put((char) positionKey, (char) (positionKey + steps));
            steps -= 2;
        }

        int stepsMinor = 25;
        int positionKeyMinor = 96;

        while (stepsMinor >= 1) {
            positionKeyMinor++;
            substituteCipher.getCodeInvert().put((char) positionKeyMinor, (char) (positionKeyMinor + stepsMinor));
            stepsMinor -= 2;
        }
    }

    /**
     * Populates the code step substitution mapping.
     */
    private void populateCodeStep() {
        for (int i = 65; i <= 89; i++) {
            substituteCipher.getCodeStep().put((char) i, (char) (i + 1));
        }

        substituteCipher.getCodeStep().put((char) 90, (char) 65);

        for (int i = 97; i <= 121; i++) {
            substituteCipher.getCodeStep().put((char) i, (char) (i + 1));
        }

        substituteCipher.getCodeStep().put((char) 122, (char) 97);
    }

    /**
     * Populates the code key substitution mapping.
     */
    private void populateCodeKey() {
        char[] keyChar = substituteCipher.getKey().toCharArray();
        substituteCipher.getCodeKey().put(keyChar[0], '\u03A3');
        substituteCipher.getCodeKey().put(keyChar[1], '\u03B1');
        substituteCipher.getCodeKey().put(keyChar[2], '\u03B2');
    }
}

