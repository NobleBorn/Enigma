package com.example.enigma;

import com.example.enigma.Model.DeCipher;
import javafx.application.Platform;
import javafx.concurrent.Task;
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

public class DeCipherController implements IEncodable{

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

    public DeCipherController(ActionEvent event){
        paneManager = PaneManager.getInstance();
        copyPaste = CopyPaste.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeCipherPage.fxml"));
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
        Parent root = (Parent) paneManager.previous();
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
    }

    private void removeText(){
        deCipherText.setText("");
    }

    private void copy(){
        copyPaste.copyText(deCipherText.getText());
        copyPaste.timer(infoLabel, "Copied", pasteButton);
    }

    private void paste(){
        deCipherText.setText(copyPaste.pasteText());
        copyPaste.timer(infoLabel,"Pasted", copyButton);
    }
}
