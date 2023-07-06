package com.example.enigma;

import com.example.enigma.Model.FileManager;
import com.example.enigma.Model.IChangable;
import com.example.enigma.Model.SecretCode;
import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;
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
    @FXML TextField newPassword;
    @FXML TextField newConfirm;
    @FXML TextField newCode;
    @FXML Label informationLabel;
    @FXML ImageView backImage;
    @FXML Button changePass;
    @FXML Button changeCode;

    private final PaneManager paneManager;
    private final User user;
    private final BorderPane borderPane;
    private final ModifyFiles modifyFiles;

    public AccountController() {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("AccountPage.fxml")));
        loader.setController(this);

        user = User.getInstance();
        paneManager = PaneManager.getInstance();
        modifyFiles = new ModifyFiles("src/main/resources/com/example/users.csv");
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
    }

    public void changePassword() {
        boolean validPass = passWordValidator(newPassword.getText());
        List<CSVRecord> records = modifyFiles.readRecords();

        if (!newPassword.getText().isEmpty() && !newConfirm.getText().isEmpty()) {
            if (newPassword.getText().equals(newConfirm.getText()) && validPass) {
                user.currentUser(user.getName());
                modifyFiles.modifyRecords(records, this);
                informationLabel.setText("Password changed");
            } else if (!validPass) {
                informationLabel.setText("Not a valid password");
            } else {
                informationLabel.setText("Passwords do not match");
            }
        } else if (newPassword.getText().isEmpty()) {
            informationLabel.setText("Provide a password");
        } else {
            informationLabel.setText("Confirm the new password");
        }
    }

    public void changeCode() {
        if (!newCode.getText().isEmpty()) {
            SecretCode secretCode = new SecretCode(newCode.getText());
            secretCode.secretCodeChange();
            informationLabel.setText("Secret code changed");
        } else {
            informationLabel.setText("Provide a new pokemon name");
        }
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
        String[] newValues = {String.valueOf(user.getUserId()), user.getName(), newPassword.getText(), user.getSecretCode()};
        modifyFiles.newRecord(records, newValues, String.valueOf(user.getUserId()));
    }
}
