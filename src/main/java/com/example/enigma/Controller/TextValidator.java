package com.example.enigma.Controller;

import com.example.enigma.Model.Client.User;
import javafx.scene.control.Label;

import java.util.Map;

/**
 * The TextValidator class is responsible for validating the input text and key before encryption or decryption.
 * It performs checks on the text and key to ensure they meet the required criteria.
 * @author Mojtaba Alizade
 */
public class TextValidator {

    private final String text;
    private final String key;
    private final User user;

    /**
     * Initializes a new instance of the TextValidator class with the provided text and key.
     * @param text The text to be validated.
     * @param key The key to be validated.
     */
    protected TextValidator(String text, String key){
        this.text = text;
        this.key = key;
        user = User.getInstance();
    }

    /**
     * Performs the validation of the text and key and triggers the encryption or decryption process if the input is valid.
     * @param errorMessage The label used to display error messages.
     * @param encryption An instance of the IEncodable interface representing the encryption or decryption logic.
     */
    protected void validator(Label errorMessage, IEncodable encryption){
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

    /**
     * Validates the input text to ensure it consists of valid characters.
     * @return True if the text is valid, false otherwise.
     */
    private boolean textValidator(){
        return text.matches("[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
    }

    /**
     * Validates the input key to ensure it consists of only letters.
     * @return True if the key is valid, false otherwise.
     */
    private boolean keyValidator(){
        return key.matches("[a-zA-Z]+");
    }
}

