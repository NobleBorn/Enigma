package com.example.enigma.Controller;

import com.example.enigma.Model.Client.User;
import javafx.scene.control.Label;

import java.util.Map;

public class TextValidator {

    private final String text;
    private final String key;
    private final User user;

    public TextValidator(String text, String key){
        this.text = text;
        this.key = key;
        user = User.getInstance();
    }

    public void validator(Label errorMessage, IEncodable encryption){
        boolean validText = textValidator();
        boolean specialCase = encryption.getClass().equals(DeCipherController.class);
        boolean validKey = keyValidator();
        user.keys();
        Map<String, String> keysInUse = user.getUserKeys();

        if (!text.isEmpty() && !key.isEmpty()) {
            if (key.length() > 3){
                errorMessage.setText("Provide only 3 letters for the key");
            } else if (keysInUse.containsKey(key) && encryption.getClass().equals(CipherController.class)){
                errorMessage.setText("This key is already in use");
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
        return text.matches(
                "[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
    }

    private boolean keyValidator(){
        return key.matches("[a-zA-Z]+");
    }
}
