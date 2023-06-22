package com.example.enigma;

import com.example.enigma.Model.AsciiCipher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CipherController {

    @FXML ImageView backButton;
    @FXML TextArea cipherText;
    @FXML TextField cipherKey;
    @FXML Button cipherTextButton;

    private Stage stage;
    private Scene scene;
    private final Parent root;
    private final PaneManager paneManager;

    public CipherController(ActionEvent event){
        paneManager = PaneManager.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CipherPage.fxml"));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            backButton.setOnMouseClicked(this::back);
            cipherTextButton.setOnAction(actionEvent -> cipher());

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }

    public void cipher(){
        AsciiCipher encrypt = new AsciiCipher(cipherText.getText(), cipherKey.getText());
        cipherText.setText(encrypt.ascii());
    }

    public void back(MouseEvent event){
        Parent root = (Parent) paneManager.previous();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }
}
