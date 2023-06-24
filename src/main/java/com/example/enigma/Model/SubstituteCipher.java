package com.example.enigma.Model;

import java.util.HashMap;
import java.util.Random;

public class SubstituteCipher {


    private final HashMap<Character, Character> codeHalf = new HashMap<>();
    private final HashMap<Character, Character> codeInvert = new HashMap<>();
    private final HashMap<Character, Character> codeStep = new HashMap<>();
    private final int codeNumber;
    private final String cipherText;

    public SubstituteCipher(String cipherText){
        this.cipherText = cipherText;
        populateCodes();
        Random random = new Random();
        codeNumber = random.nextInt(3) + 1;

    }

    private void populateCodes(){

        for (int i = 65; i <= 77; i++){
            codeHalf.put((char) i, (char) (i+13));
        }

        int steps = 25;
        int positionKey = 64;

        while (steps != 1){
            positionKey++;
            codeInvert.put((char) positionKey, (char) (positionKey + steps));
            steps -= 2;
        }

        for (int i = 65; i <= 89; i++){
            codeStep.put((char) i, (char) (i+1));
        }

        codeStep.put((char) 90, (char) (65));

    }

    public String substitute(){

        char[] substituteChar = cipherText.toCharArray();

        HashMap<Character, Character> codeMap = switch (codeNumber) {
            case 1 -> codeHalf;
            case 2 -> codeInvert;
            default -> codeStep;
        };

        for (int i = 0; i < substituteChar.length; i++) {
            char currentChar = Character.toUpperCase(substituteChar[i]);
            if (codeMap.containsKey(currentChar)) {
                char substitutedChar = codeMap.get(currentChar);
                substituteChar[i] = substitutedChar;
            }
        }

        return String.valueOf(substituteChar);
    }

}
