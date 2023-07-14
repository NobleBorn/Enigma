package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Client.SignUpLogic;
import com.example.enigma.Model.Timer;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


/**
 * The SignUpController class handles the signup functionality and user interface events related to user registration
 * in the Enigma application.
 * @author Mojtaba Alizade
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
     * @param passWordSign The password field containing the password.
     * @param secretCode   The password field containing the secret code.
     */
    public void registerUser(TextField userNameSign, PasswordField passWordSign, PasswordField secretCode) {
        if (!userNameSign.getText().isEmpty() && !passWordSign.getText().isEmpty()
                && !secretCode.getText().isEmpty()) {

            String name = userNameSign.getText().trim();
            String pass = passWordSign.getText().trim();
            String code = secretCode.getText().trim();

            boolean validUserName = userNameValidator(name);
            int passWordLength = pass.length();

            if (validUserName && passWordLength >= 4 && passWordLength <= 10){
                String[] filePaths = {
                        "src/main/resources/com/example/enigma/users.csv",
                        "src/main/resources/com/example/enigma/rememberUser.csv",
                        "src/main/resources/com/example/enigma/trophy.csv"
                };

                SignUpLogic signUpLogic = new SignUpLogic(name, pass, code, filePaths);

                if (signUpLogic.addUser()){
                    changeStyle();
                    Timer takeTime = new Timer();
                    takeTime.timer(this);
                } else  {
                    errorLabel.setText("Username already exists!");
                    userNameSign.setText("");
                    passWordSign.setText("");
                }
            } else if (!validUserName) {
                errorLabel.setText("Username between 3-10 characters, only english letter!");
            } else {
                errorLabel.setText("Password should be of length 4-10 characters!");
            }


        } else if (userNameSign.getText().isEmpty()) {
            errorLabel.setText("Please provide username");
        } else if (passWordSign.getText().isEmpty()) {
            errorLabel.setText("Please provide password");
        } else {
            errorLabel.setText("Please provide a pokemon name");
        }
    }

    /**
     * Validates the username using a regular expression pattern.
     *
     * @param text The username to validate.
     * @return true if the username is valid, false otherwise.
     */
    private boolean userNameValidator(String text) {
        boolean valid = text.matches("[a-zA-Z0-9]+");
        boolean validLength = text.length() >= 3 && text.length() <= 10;

        return valid && validLength;
    }


    /**
     * During timer
     */
    @Override
    public void duringTimer() {
        errorLabel.setText("Account created!");
    }

    /**
     * After timer
     */
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

