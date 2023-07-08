package com.example.enigma.Controller;

import com.example.enigma.Model.Encryption.DeCipher;
import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Trophy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DeCipherController implements IEncodable, ITimer {

    @FXML ImageView backArrow;
    @FXML TextArea deCipherText;
    @FXML Label errorLabel;
    @FXML TextField deCipherKey;
    @FXML Button deCipherButton;
    @FXML Button cleanButton;
    @FXML Button pasteButton;
    @FXML Button copyButton;
    @FXML Label infoLabel;

    private Stage stage;
    private Scene scene;
    private final PaneManager paneManager;
    private final CopyPaste copyPaste;
    private final String[] operation = {"copy", "paste"};
    private String currentOperation;

    public DeCipherController(ActionEvent event){
        paneManager = PaneManager.getInstance();
        copyPaste = CopyPaste.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/enigma/DeCipherPage.fxml"));
        fxmlLoader.setController(this);

        try {
            Parent root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            setActions();

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setActions(){
        backArrow.setOnMouseClicked(this::back);
        deCipherButton.setOnAction(actionEvent -> deCipher());
        cleanButton.setOnAction(actionEvent -> removeText());
        copyButton.setOnAction(actionEvent -> copy());
        pasteButton.setOnAction(actionEvent -> paste());
    }

    private void back(MouseEvent event){
        Parent root = paneManager.getBorderPane();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

    private void deCipher(){
        TextValidator textValidator = new TextValidator(deCipherText.getText(), deCipherKey.getText());
        textValidator.validator(errorLabel, this);
    }

    @Override
    public void ciphering() {
        errorLabel.setText("");
        DeCipher deCipher = new DeCipher(deCipherText.getText(), deCipherKey.getText());
        deCipherText.setText(deCipher.getDeCodedText());

        Trophy trophy = new Trophy();

        if (trophy.getDecipherTrophy() < 20){
            trophy.updateTrophy(3);
        }
    }

    private void removeText(){
        deCipherText.setText("");
    }

    private void copy(){
        currentOperation = operation[0];
        copyPaste.copyText(deCipherText.getText());
        copyPaste.countTime(this);
    }

    private void paste(){
        currentOperation = operation[1];
        deCipherText.setText(copyPaste.pasteText());
        copyPaste.countTime(this);
    }

    @Override
    public void duringTimer() {
        if (currentOperation.equals(operation[0])){
            infoLabel.setText("Copied");
            pasteButton.setDisable(true);
        } else {
            infoLabel.setText("Pasted");
            copyButton.setDisable(true);
        }
    }

    @Override
    public void afterTimer() {
        infoLabel.setText("");

        if (currentOperation.equals(operation[0])){
            pasteButton.setDisable(false);
        } else {
            copyButton.setDisable(false);
        }
    }
}
