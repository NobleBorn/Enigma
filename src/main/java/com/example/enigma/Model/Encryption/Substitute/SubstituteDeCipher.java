package com.example.enigma.Model.Encryption.Substitute;

import org.apache.commons.csv.CSVParser;

import java.io.IOException;

public class SubstituteDeCipher {

    private final SubstituteParent substituteDeCipher;

    public SubstituteDeCipher(String deCipherText, String deCipherKey){
        substituteDeCipher = new SubstituteParent(deCipherText, deCipherKey);
        populateCodes();
        getCodeNumber();
    }

    private void populateCodes(){
        populateCodeHalf();
        populateCodeInvert();
        populateCodeStep();
        populateCodeKey();
    }

    private void getCodeNumber(){
        int userId = substituteDeCipher.getUser().getUserId();

        try (CSVParser csvParser = substituteDeCipher.getCsv().readFromFile()) {
            csvParser.forEach(record -> {
                if (Integer.parseInt(record.get(0)) == userId && record.get(2).equals(substituteDeCipher.getKey())){
                    substituteDeCipher.setCodeNumber(Integer.parseInt(record.get(1)));
                    substituteDeCipher.setFound(true);
                }
            });

            if (!substituteDeCipher.isFound()) System.out.println("Not found");
            System.out.println("CSV file read successfully!");
        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    public String deSubstitute(){
        return substituteDeCipher.substitute(substituteDeCipher.getText());
    }

    private void populateCodeHalf(){
        for (int i = 90; i >= 78; i--) {
            substituteDeCipher.getCodeHalf().put((char) i, (char) (i - 13));
        }

        for (int i = 122; i >= 110; i--) {
            substituteDeCipher.getCodeHalf().put((char) i, (char) (i - 13));
        }
    }

    private void populateCodeInvert(){
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

    private void populateCodeStep(){
        for (int i = 90; i >= 66; i--){
            substituteDeCipher.getCodeStep().put((char) i, (char) (i - 1));
        }

        substituteDeCipher.getCodeStep().put((char) 65, (char) 90);

        for (int i = 122; i >= 98; i--){
            substituteDeCipher.getCodeStep().put((char) i, (char) (i - 1));
        }

        substituteDeCipher.getCodeStep().put((char) 97, (char) 122);
    }

    private void populateCodeKey(){
        char[] keyChar = substituteDeCipher.getKey().toCharArray();
        substituteDeCipher.getCodeKey().put('\u03A3', keyChar[0]);
        substituteDeCipher.getCodeKey().put('\u03B1', keyChar[1]);
        substituteDeCipher.getCodeKey().put('\u03B2', keyChar[2]);
    }


}
