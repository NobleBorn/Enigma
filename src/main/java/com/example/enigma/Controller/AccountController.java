package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.IChangable;
import com.example.enigma.Model.Client.SecretCode;
import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.ModifyFiles;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.layout.BorderPane;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccountController implements IChangable {

    @FXML TextField userName;
    @FXML TextField oldPassword;
    @FXML TextField newPassword;
    @FXML TextField oldCode;
    @FXML TextField newCode;
    @FXML Label informationLabel;
    @FXML ImageView backImage;
    @FXML Button changePass;
    @FXML Button changeCode;
    @FXML CheckBox rememberSecret;

    private final PaneManager paneManager;
    private final User user;
    private final BorderPane borderPane;
    private final String[] operation = {"changePass", "rememberUser"};
    private String currentOperation;
    private final ModifyFiles modifyFiles;
    private final ModifyFiles modifyRemember;

    public AccountController() {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/enigma/AccountPage.fxml")));
        loader.setController(this);

        user = User.getInstance();
        user.currentUser(user.getName());
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

        backImage.setOnMouseClicked(mouseEvent -> back());
        userName.setText(user.getName());
        changePass.setOnAction(actionEvent -> changePassword());
        changeCode.setOnAction(actionEvent -> changeCode());
        rememberSecret.setOnAction(actionEvent -> changeRememberUser());

        if (user.isRemembered()){
            rememberSecret.setDisable(false);
            rememberSecret.setSelected(true);
        }
    }

    public void changePassword() {
        boolean validPass = passWordValidator(newPassword.getText());
        List<CSVRecord> records = modifyFiles.readRecords();

        if (!oldPassword.getText().isEmpty() && !newPassword.getText().isEmpty()) {
            if (oldPassword.getText().equals(user.getPassword()) && validPass) {
                currentOperation = operation[0];
                modifyFiles.modifyRecords(records, this);

                changeStyle();
                informationLabel.setText("Password changed");
            } else if (!validPass) {
                informationLabel.setText("Not a valid password");
            } else {
                informationLabel.setText("Incorrect password!");
            }
        } else if (oldPassword.getText().isEmpty()) {
            informationLabel.setText("Provide the current password");
        } else {
            informationLabel.setText("Provide a new password");
        }
    }

    public void changeCode() {
        if (!newCode.getText().isEmpty() && !oldCode.getText().isEmpty()) {
            if (oldCode.getText().equals(user.getSecretCode())){
                SecretCode secretCode = new SecretCode(newCode.getText());
                secretCode.secretCodeChange();
                rememberSecret.setSelected(false);
                changeRememberUser();
                rememberSecret.setDisable(true);

                changeStyle();
                informationLabel.setText("Secret code changed");
            } else {
                informationLabel.setText("Incorrect code!");
            }
        } else if (oldCode.getText().isEmpty()){
            informationLabel.setText("Provide the old code");
        } else {
            informationLabel.setText("Provide a new code");
        }
    }

    private void changeRememberUser(){
        currentOperation = operation[1];
        List<CSVRecord> records = modifyRemember.readRecords();
        modifyRemember.modifyRecords(records, this);
    }

    private boolean passWordValidator(String text) {
        return text.matches("[a-zA-Z0-9!@#$%^&*()-=_+\\\\[\\\\]{}|;':\\\",./<>?\\s]+");
    }

    private void back(){
        Node nodeRight = paneManager.getPreviousNodes();
        Node nodeLeft = paneManager.getPreviousNodes();
        Node nodeCenter = paneManager.getPreviousNodes();
        Node nodeTop = paneManager.getPreviousNodes();
        borderPane.setTop(nodeTop);
        borderPane.setLeft(nodeLeft);
        borderPane.setCenter(nodeCenter);
        borderPane.setRight(nodeRight);
    }

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

    private void changeStyle(){
        informationLabel.setStyle("-fx-text-fill: White; -fx-effect: innershadow(gaussian, #ffffff, 10, 0, 0, 0);" +
                " -fx-effect: dropshadow(gaussian, #ffffff, 10, 0, 0, 0);");
    }
}
