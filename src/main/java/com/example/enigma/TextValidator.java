package com.example.enigma;

import javafx.scene.control.Label;

public class TextValidator {

    String text;
    String key;

    public TextValidator(String text, String key){
        this.text = text;
        this.key = key;
    }

    public void validator(Label errorMessage, IEncodable encryption){
        boolean validText = textValidator();
        boolean specialCase = encryption.getClass().equals(DeCipherController.class);
        boolean validKey = keyValidator();

        if (!text.isEmpty() && !key.isEmpty()) {
            if (key.length() > 3){
                errorMessage.setText("Provide only 3 letters for the key");
            } else if ((specialCase || validText) && validKey) {
                encryption.ciphering();
            } else if (!validText) {
                errorMessage.setText("Provide only English letters");
            } else {
                errorMessage.setText("Provide only letters for the key");
            }
        } else if (text.isEmpty()) {
            errorMessage.setText("Provide a text to cipher");
        } else {
            errorMessage.setText("Provide a key");
        }
    }

    private boolean textValidator(){
        return text.matches("[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
    }

    private boolean keyValidator(){
        return key.matches("[a-zA-Z]+");
    }
}
