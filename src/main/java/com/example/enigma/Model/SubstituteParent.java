package com.example.enigma.Model;

import java.util.HashMap;

public class SubstituteParent {

    private final HashMap<Character, Character> codeHalf = new HashMap<>();
    private final HashMap<Character, Character> codeInvert = new HashMap<>();
    private final HashMap<Character, Character> codeStep = new HashMap<>();
    private final HashMap<Character, Character> codeKey = new HashMap<>();
    private int codeNumber;
    private final String Text;
    private final String Key;
    private final User user = User.getInstance();
    private final FileManager csv;
    private boolean found = false;

    public SubstituteParent(String Text, String Key){
        this.Text = Text;
        this.Key = Key;
        csv = new FileManager("src/main/resources/com/example/keys.csv");
    }

    public HashMap<Character, Character> getCodeHalf() {
        return codeHalf;
    }

    public FileManager getCsv() {
        return csv;
    }

    public HashMap<Character, Character> getCodeInvert() {
        return codeInvert;
    }

    public HashMap<Character, Character> getCodeKey() {
        return codeKey;
    }

    public HashMap<Character, Character> getCodeStep() {
        return codeStep;
    }

    public void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

    public String substitute(String code){
        char[] substituteChar = code.toCharArray();

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

    public User getUser() {
        return user;
    }

    public String getText() {
        return Text;
    }

    public String getKey() {
        return Key;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public boolean isFound() {
        return found;
    }
}
