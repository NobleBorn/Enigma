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

/**
 * The `DeCipherController` class handles the decryption functionality and manages the decipher page.
 * It allows users to enter text and a decipher key, perform decryption and copy/paste text
 * @see IEncodable
 * @see ITimer
 */
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

    /**
     * Constructs a DeCipherController object and initializes the decipher page of the Enigma application.
     *
     * @param event The action event that triggered the decipher page.
     */
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

    /**
     * Sets the actions for FXML nodes
     */
    private void setActions(){
        backArrow.setOnMouseClicked(this::back);
        deCipherButton.setOnAction(actionEvent -> deCipher());
        cleanButton.setOnAction(actionEvent -> removeText());
        copyButton.setOnAction(actionEvent -> copy());
        pasteButton.setOnAction(actionEvent -> paste());
    }

    /**
     * Navigates back to the previous page.
     *
     * @param event The mouse event that triggered the back action.
     */
    private void back(MouseEvent event){
        Parent root = paneManager.getBorderPane();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Start of the decryption process when decipher text button is clicked.
     */
    private void deCipher(){
        TextValidator textValidator = new TextValidator(deCipherText.getText(), deCipherKey.getText());
        textValidator.validator(errorLabel, this);
    }

    /**
     * Writes the decoded text on the text area
     * Adds to user trophy score
     */
    @Override
    public void ciphering() {
        errorLabel.setText("");
        DeCipher deCipher = new DeCipher(deCipherText.getText(), deCipherKey.getText());
        deCipherText.setText(deCipher.getDeCodedText());

        addToUserTrophy();
    }

    /**
     * Removes the text from the text area.
     */
    private void removeText(){
        deCipherText.setText("");
    }

    /**
     * Copies the cipher text to the clipboard.
     */
    private void copy(){
        currentOperation = operation[0];
        copyPaste.copyText(deCipherText.getText());
        copyPaste.countTime(this);
    }

    /**
     * Pastes the text from the clipboard to the cipher text area.
     */
    private void paste(){
        currentOperation = operation[1];
        deCipherText.setText(copyPaste.pasteText());
        copyPaste.countTime(this);
    }

    /**
     * Operations during timer
     */
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

    /**
     * Operations after timer
     */
    @Override
    public void afterTimer() {
        infoLabel.setText("");

        if (currentOperation.equals(operation[0])){
            pasteButton.setDisable(false);
        } else {
            copyButton.setDisable(false);
        }
    }

    /**
     * Raise user trophy score
     */
    private void addToUserTrophy(){
        Trophy trophy = new Trophy();

        if (trophy.getDecipherTrophy() < 20){
            trophy.updateTrophy(3);
        }
    }
}
