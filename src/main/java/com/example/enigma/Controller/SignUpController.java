package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Client.SignUp.SignUpLogic;
import com.example.enigma.Model.Timer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * The SignUpController class handles the signup functionality and user interface events related to user registration
 * in the Enigma application.
 */
public class SignUpController implements ITimer {

    private final BorderPane borderPane;
    private final Label errorLabel;
    private final PaneManager paneManager;

    /**
     * Constructs a SignUpController object with the specified error label.
     *
     * @param errorLabel The label used to display error messages.
     */
    public SignUpController(Label errorLabel) {
        this.errorLabel = errorLabel;
        paneManager = PaneManager.getInstance();
        borderPane = paneManager.getBorderPane();
    }

    /**
     * Registers a new user with the provided username, password, and secret code.
     *
     * @param userNameSign The text field containing the username.
     * @param passWordSign The text field containing the password.
     * @param secretCode   The text field containing the secret code.
     */
    public void registerUser(TextField userNameSign, TextField passWordSign, TextField secretCode) {
        boolean validUserName = userNameValidator(userNameSign.getText());

        int passWordLength = passWordSign.getLength();
        if (!userNameSign.getText().isEmpty() && !passWordSign.getText().isEmpty() && validUserName
                && !secretCode.getText().isEmpty() && passWordLength >= 4) {
            try {
                new SignUpLogic(userNameSign.getText(), passWordSign.getText(), secretCode.getText());
                changeStyle();
                Timer takeTime = new Timer();
                takeTime.timer(this);
            } catch (IOException e) {
                errorLabel.setText("Username already exists");
                userNameSign.setText("");
                passWordSign.setText("");
            }
        } else if (userNameSign.getText().isEmpty()) {
            errorLabel.setText("Please provide username");
        } else if (passWordSign.getText().isEmpty()) {
            errorLabel.setText("Please provide password");
        } else if (secretCode.getText().isEmpty()) {
            errorLabel.setText("Please provide a pokemon name");
        } else if (!validUserName) {
            errorLabel.setText("Username can contain only letters and numbers");
        } else {
            errorLabel.setText("Password at least of length four");
        }
    }

    /**
     * Validates the username using a regular expression pattern.
     *
     * @param text The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    private boolean userNameValidator(String text) {
        return text.matches("[a-zA-Z0-9]+");
    }

    @Override
    public void duringTimer() {
        errorLabel.setText("Account created!");
    }

    @Override
    public void afterTimer() {
        borderPane.setCenter(paneManager.getPreviousNodes());
    }

    /**
     * Changes the style of the error label to indicate a successful account creation.
     */
    private void changeStyle() {
        errorLabel.setStyle("-fx-text-fill: White; -fx-effect: innershadow(gaussian, #ffffff, 10, 0, 0, 0);"
                + " -fx-effect: dropshadow(gaussian, #ffffff, 10, 0, 0, 0);");
    }
}

