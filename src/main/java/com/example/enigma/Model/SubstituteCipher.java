package com.example.enigma.Model;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.Random;

public class SubstituteCipher {

    private final SubstituteParent substituteCipher;

    public SubstituteCipher(String cipherText, String cipherKey){
        substituteCipher = new SubstituteParent(cipherText, cipherKey);
        populateCodes();
        Random random = new Random();
        int codeNumber = random.nextInt(3) + 1;
        substituteCipher.setCodeNumber(codeNumber);
        saveCodeNumber();
    }

    private void populateCodes(){
        populateCodeHalf();
        populateCodeInvert();
        populateCodeStep();
        populateCodeKey();
    }

    public String substitute(){
        return substituteCipher.substitute(substituteCipher.getText());
    }

    private void saveCodeNumber(){
        try (CSVPrinter csvPrinter = substituteCipher.getCsv().writeToFile()){
            csvPrinter.printRecord(String.valueOf(substituteCipher.getUser().getUserId()),
                    String.valueOf(substituteCipher.getCodeNumber()), substituteCipher.getKey());
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }


    private void populateCodeHalf(){
        for (int i = 65; i <= 77; i++){
            substituteCipher.getCodeHalf().put((char) i, (char) (i+13));
        }

        for (int i = 97; i <= 109; i++){
            substituteCipher.getCodeHalf().put((char) i, (char) (i+13));
        }
    }

    private void populateCodeInvert(){
        int steps = 25;
        int positionKey = 64;

        while (steps >= 1){
            positionKey++;
            substituteCipher.getCodeInvert().put((char) positionKey, (char) (positionKey + steps));
            steps -= 2;
        }

        int stepsMinor = 25;
        int positionKeyMinor = 96;

        while (stepsMinor >= 1){
            positionKeyMinor++;
            substituteCipher.getCodeInvert().put((char) positionKeyMinor, (char) (positionKeyMinor + stepsMinor));
            stepsMinor -= 2;
        }
    }

    private void populateCodeStep(){
        for (int i = 65; i <= 89; i++){
            substituteCipher.getCodeStep().put((char) i, (char) (i+1));
        }

        substituteCipher.getCodeStep().put((char) 90, (char) 65);

        for (int i = 97; i <= 121; i++){
            substituteCipher.getCodeStep().put((char) i, (char) (i+1));
        }

        substituteCipher.getCodeStep().put((char) 122, (char) 97);
    }

    private void populateCodeKey(){
        char[] keyChar = substituteCipher.getKey().toCharArray();
        substituteCipher.getCodeKey().put(keyChar[0], '\u03A3');
        substituteCipher.getCodeKey().put(keyChar[1], '\u03B1');
        substituteCipher.getCodeKey().put(keyChar[2], '\u03B2');
    }
}
