package com.example.enigma.Model;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.HashMap;

public class SubstituteDeCipher {

    private final HashMap<Character, Character> codeHalf = new HashMap<>();
    private final HashMap<Character, Character> codeInvert = new HashMap<>();
    private final HashMap<Character, Character> codeStep = new HashMap<>();
    private final HashMap<Character, Character> codeKey = new HashMap<>();
    private int codeNumber;
    private final String deCipherText;
    private final String deCipherKey;
    private final User user = User.getInstance();
    private final FileManager csv;
    private boolean found = false;

    public SubstituteDeCipher(String deCipherText, String deCipherKey){
        this.deCipherText = deCipherText;
        this.deCipherKey = deCipherKey;
        csv = new FileManager("src/main/resources/com/example/keys.csv");
        populateCodes();
        getCodeNumber();
    }

    private void populateCodes(){
        for (int i = 90; i >= 78; i--) {
            codeHalf.put((char) i, (char) (i - 13));
        }

        for (int i = 122; i >= 110; i--) {
            codeHalf.put((char) i, (char) (i - 13));
        }

        int steps = 25;
        int positionKey = 91;

        while (steps >= 1) {
            positionKey--;
            codeInvert.put((char) positionKey, (char) (positionKey - steps));
            steps -= 2;
        }

        int stepsMinor = 25;
        int positionKeyMinor = 123;

        while (stepsMinor >= 1) {
            positionKeyMinor--;
            codeInvert.put((char) positionKeyMinor, (char) (positionKeyMinor - stepsMinor));
            stepsMinor -= 2;
        }

        for (int i = 90; i >= 66; i--){
            codeStep.put((char) i, (char) (i - 1));
        }

        codeStep.put((char) 65, (char) 90);

        for (int i = 122; i >= 98; i--){
            codeStep.put((char) i, (char) (i - 1));
        }

        codeStep.put((char) 97, (char) 122);

        char[] keyChar = deCipherKey.toCharArray();
        codeKey.put('\u03A3', keyChar[0]);
        codeKey.put('\u03B1', keyChar[1]);
        codeKey.put('\u03B2', keyChar[2]);
    }

    private void getCodeNumber(){
        int userId = user.getUserId();

        try (CSVParser csvParser = csv.readFromFile()) {
            csvParser.forEach(record -> {
                if (Integer.parseInt(record.get(0)) == userId && record.get(2).equals(deCipherKey)){
                    codeNumber = Integer.parseInt(record.get(1));
                    found = true;
                } else if (!found){
                    System.out.println("Not found");
                }
            });

            System.out.println("CSV file read successfully!");

        } catch (IOException e) {
            System.out.println("Error occurred while reading the CSV file: " + e.getMessage());
        }
    }

    public String deSubstitute(){
        char[] substituteChar = deCipherText.toCharArray();

        HashMap<Character, Character> codeMap = switch (codeNumber) {
            case 1 -> codeHalf;
            case 2 -> codeInvert;
            default -> codeStep;
        };

        for (int i = 0; i < substituteChar.length; i++) {
            char currentChar = substituteChar[i];

            if (codeKey.containsKey(currentChar)){
                int substitutedChar = codeKey.get(currentChar);
                substituteChar[i] = (char) substitutedChar;
            } else if (codeMap.containsKey(currentChar)) {
                char substitutedChar = codeMap.get(currentChar);
                substituteChar[i] = substitutedChar;
            } else if (codeMap.containsValue(currentChar)){
                for (char key : codeMap.keySet()){
                    if (codeMap.get(key) == currentChar){
                        substituteChar[i] = key;
                    }
                }
            }
        }

        return String.valueOf(substituteChar);
    }
}
