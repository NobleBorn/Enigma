package com.example.enigma;

import com.example.enigma.Model.Cipher;
import com.example.enigma.Model.DeCipher;
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

public class DeCipherController {

    @FXML ImageView backArrow;
    @FXML TextArea deCipherText;
    @FXML Label errorLabel;
    @FXML TextField deCipherKey;
    @FXML Button deCipherButton;

    private Stage stage;
    private Scene scene;
    private final PaneManager paneManager;

    public DeCipherController(ActionEvent event){
        paneManager = PaneManager.getInstance();
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
    }

    private void back(MouseEvent event){
        Parent root = (Parent) paneManager.previous();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

    private void deCipher(){
        String text = deCipherText.getText();
        String key = deCipherKey.getText();

        if (!text.isEmpty() && !key.isEmpty()) {
            if (deCipherKey.getText().length() > 3){
                errorLabel.setText("Provide only 3 letters for the key");
            } else {
                errorLabel.setText("");
                DeCipher deCipher = new DeCipher(text, key);
                deCipherText.setText(deCipher.getDeCodedText());
            }

        } else if (text.isEmpty()) {
            errorLabel.setText("Provide a text to cipher");
        } else {
            errorLabel.setText("Provide a key");
        }
    }
}
