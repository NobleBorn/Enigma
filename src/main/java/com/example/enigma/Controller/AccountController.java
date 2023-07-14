package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.IChangeable;
import com.example.enigma.Model.Client.SecretCode;
import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.ModifyFiles;
import com.example.enigma.Model.Timer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * The AccountController class manages the account page functionality and user interface events in the Enigma application.
 * @see IChangeable
 * @see ITimer
 * @author Mojtaba Alizade
 */
public class AccountController implements IChangeable, ITimer {

    @FXML TextField userName;
    @FXML PasswordField oldPassword;
    @FXML PasswordField newPassword;
    @FXML PasswordField oldCode;
    @FXML PasswordField newCode;
    @FXML Label informationLabel;
    @FXML ImageView backImage;
    @FXML Button changePass;
    @FXML Button changeCode;
    @FXML CheckBox rememberSecret;

    private final PaneManager paneManager;
    private final User user;
    private final BorderPane borderPane;
    private final String[] operation = {"changePass", "rememberUser", "changeCode"};
    private String currentOperation;
    private final ModifyFiles modifyFiles;
    private final ModifyFiles modifyRemember;
    private final Timer takeTime = new Timer();
    private final String prev;

    /**
     * Constructs an AccountController object and initializes the account page of the Enigma application.
     */
    public AccountController(String prev) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/enigma/AccountPage.fxml")));
        loader.setController(this);

        user = User.getInstance();

        paneManager = PaneManager.getInstance();
        modifyFiles = new ModifyFiles("src/main/resources/com/example/enigma/users.csv");
        modifyRemember = new ModifyFiles("src/main/resources/com/example/enigma/rememberUser.csv");
        borderPane = paneManager.getBorderPane();

        try {
            Parent node = loader.load();
            borderPane.setCenter(node);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        this.prev = prev;

        fxmlActions();
        restrictSpaces(oldPassword);
        restrictSpaces(newPassword);
        restrictSpaces(oldCode);
        restrictSpaces(newCode);

        user.currentUser(user.getName());
        if (user.isRemembered()){
            rememberSecret.setDisable(false);
            rememberSecret.setSelected(true);
        }
    }

    /**
     * Changes the user's password.
     */
    public void changePassword() {
        boolean validPass = passWordValidator(newPassword.getText());
        List<CSVRecord> records = modifyFiles.readRecords();

        if (!oldPassword.getText().isEmpty() && !newPassword.getText().isEmpty()) {
            if (oldPassword.getText().equals(user.getPassword()) && validPass) {
                currentOperation = operation[0];
                modifyFiles.modifyRecords(records, this);

                takeTime.timer(this);

            } else if (!validPass) {
                informationLabel.setText("Password length of 4-10!");
            } else {
                informationLabel.setText("Incorrect password!");
            }
        } else if (oldPassword.getText().isEmpty()) {
            informationLabel.setText("Provide the current password");
        } else {
            informationLabel.setText("Provide a new password");
        }
    }

    /**
     * Changes the user's secret code.
     */
    public void changeCode() {
        if (!newCode.getText().isEmpty() && !oldCode.getText().isEmpty()) {
            if (oldCode.getText().equals(user.getSecretCode())){
                currentOperation = operation[2];

                SecretCode secretCode = new SecretCode(newCode.getText(),
                        "src/main/resources/com/example/enigma/users.csv");
                secretCode.secretCodeChange();
                rememberSecret.setSelected(false);
                changeRememberUser();
                rememberSecret.setDisable(true);

                takeTime.timer(this);

            } else {
                informationLabel.setText("Incorrect code!");
            }
        } else if (oldCode.getText().isEmpty()){
            informationLabel.setText("Provide the old code");
        } else {
            informationLabel.setText("Provide a new code");
        }
    }

    /**
     * Restricts the space character in the field
     * @param passwordField The password field being restricted
     */
    private void restrictSpaces(PasswordField passwordField) {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                passwordField.setText(newValue.replaceAll(" ", ""));
            }
        });
    }

    /**
     * Changes the state of remembering the user.
     */
    private void changeRememberUser(){
        currentOperation = operation[1];
        List<CSVRecord> records = modifyRemember.readRecords();
        modifyRemember.modifyRecords(records, this);
    }

    /**
     * Validates the password based on the specified regular expression pattern.
     *
     * @param text The password string to validate.
     * @return {@code true} if the password is valid, {@code false} otherwise.
     */
    private boolean passWordValidator(String text) {
        boolean validPass = text.matches("[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
        boolean validLength = text.length() >= 4 && text.length() <= 10;
        return validPass && validLength;
    }

    /**
     * Navigates back to the previous page.
     */
    private void back(){
        paneManager.removePrevious();

        Node nodeRight = paneManager.getPreviousNodes();
        Node nodeLeft = paneManager.getPreviousNodes();
        Node nodeCenter = paneManager.getPreviousNodes();
        Node nodeTop = paneManager.getPreviousNodes();

        if (prev.equals("KeysPage")){
            new KeysPageController();
        } else {
            borderPane.setTop(nodeTop);
            borderPane.setLeft(nodeLeft);
            borderPane.setCenter(nodeCenter);
            borderPane.setRight(nodeRight);
        }
    }

    /**
     * Modifies records of CSV file based on which operation it is
     * @param records The list of CSV records to be modified.
     */
    @Override
    public void modify(List<CSVRecord> records) {
        if (currentOperation.equals(operation[0])){
            String[] newValues = {String.valueOf(user.getUserId()), user.getName(), newPassword.getText(),
                    user.getSecretCode()};
            modifyFiles.newRecord(records, newValues, String.valueOf(user.getUserId()));
        } else {
            String[] newValues = {String.valueOf(user.getUserId()), String.valueOf(rememberSecret.isSelected())};
            modifyRemember.newRecord(records, newValues, String.valueOf(user.getUserId()));
        }

    }

    /**
     * Changes the style of the information label.
     */
    private void changeStyle(){
        informationLabel.setStyle("-fx-text-fill: White; -fx-effect: innershadow(gaussian, #ffffff, 10, 0, 0, 0);" +
                " -fx-effect: dropshadow(gaussian, #ffffff, 10, 0, 0, 0);");
    }

    private void changeStyleBack(){
        informationLabel.setStyle("-fx-text-fill: #ff0d0d; -fx-effect: dropshadow(gaussian, #c35252, 10, 0, 0, 0);");
    }

    /**
     * Sets the actions for the FXML nodes
     */
    private void fxmlActions(){
        backImage.setOnMouseClicked(mouseEvent -> back());
        userName.setText(user.getName());
        changePass.setOnAction(actionEvent -> changePassword());
        changeCode.setOnAction(actionEvent -> changeCode());
        rememberSecret.setOnAction(actionEvent -> changeRememberUser());
    }

    /**
     * During delay
     */
    @Override
    public void duringTimer() {
        changeStyle();
        if (currentOperation.equals(operation[0])){
            informationLabel.setText("Password changed");
        } else {
            informationLabel.setText("Secret code changed");
        }
    }

    /**
     * After the delay
     */
    @Override
    public void afterTimer() {
        changeStyleBack();
        informationLabel.setText("");

        if (currentOperation.equals(operation[0])){
            oldPassword.setText("");
            newPassword.setText("");
        }  else {
            informationLabel.setText("");
            oldCode.setText("");
            newCode.setText("");
        }
    }
}

