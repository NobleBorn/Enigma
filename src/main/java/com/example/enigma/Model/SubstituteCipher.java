package com.example.enigma.Model;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class SubstituteCipher {

    private final HashMap<Character, Character> codeHalf = new HashMap<>();
    private final HashMap<Character, Character> codeInvert = new HashMap<>();
    private final HashMap<Character, Character> codeStep = new HashMap<>();
    private final HashMap<Character, Character> codeKey = new HashMap<>();
    private final int codeNumber;
    private final String cipherText;
    private final String cipherKey;
    private final User user = User.getInstance();
    private final FileManager csv;

    public SubstituteCipher(String cipherText, String cipherKey){
        this.cipherText = cipherText;
        this.cipherKey = cipherKey;
        csv = new FileManager("src/main/resources/com/example/keys.csv");
        populateCodes();
        Random random = new Random();
        codeNumber = random.nextInt(3) + 1;
        saveCodeNumber();
    }

    private void populateCodes(){
        for (int i = 65; i <= 77; i++){
            codeHalf.put((char) i, (char) (i+13));
        }

        for (int i = 97; i <= 109; i++){
            codeHalf.put((char) i, (char) (i+13));
        }

        int steps = 25;
        int positionKey = 64;

        while (steps >= 1){
            positionKey++;
            codeInvert.put((char) positionKey, (char) (positionKey + steps));
            steps -= 2;
        }

        int stepsMinor = 25;
        int positionKeyMinor = 96;

        while (stepsMinor >= 1){
            positionKeyMinor++;
            codeInvert.put((char) positionKeyMinor, (char) (positionKeyMinor + stepsMinor));
            stepsMinor -= 2;
        }

        for (int i = 65; i <= 89; i++){
            codeStep.put((char) i, (char) (i+1));
        }

        codeStep.put((char) 90, (char) 65);

        for (int i = 97; i <= 121; i++){
            codeStep.put((char) i, (char) (i+1));
        }

        codeStep.put((char) 122, (char) 97);

        char[] keyChar = cipherKey.toCharArray();
        codeKey.put(keyChar[0], '\u03A3');
        codeKey.put(keyChar[1], '\u03B1');
        codeKey.put(keyChar[2], '\u03B2');
    }

    public String substitute(){
        char[] substituteChar = cipherText.toCharArray();

        HashMap<Character, Character> codeMap = switch (codeNumber) {
            case 1 -> codeHalf;
            case 2 -> codeInvert;
            default -> codeStep;
        };

        for (int i = 0; i < substituteChar.length; i++) {
            char currentChar = substituteChar[i];

            if (codeKey.containsKey(currentChar)){
                char substitutedChar = codeKey.get(currentChar);
                substituteChar[i] = substitutedChar;
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

    private void saveCodeNumber(){
        try (CSVPrinter csvPrinter = csv.writeToFile()){
            csvPrinter.printRecord(String.valueOf(user.getUserId()), String.valueOf(codeNumber), cipherKey);
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }
    }

    public HashMap<Character, Character> getCodeHalf() {
        return codeHalf;
    }
}
