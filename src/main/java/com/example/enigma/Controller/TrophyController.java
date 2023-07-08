package com.example.enigma.Controller;

import com.example.enigma.Model.Trophy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TrophyController {

    @FXML Pane cipherTrophy;
    @FXML Pane keyTrophy;
    @FXML Pane decipherTrophy;
    @FXML ImageView cipherImage;
    @FXML ImageView keyImage;
    @FXML ImageView decipherImage;
    @FXML ImageView goingBack;

    private final PaneManager paneManager;
    private final BorderPane borderPane;

    public TrophyController(){
        paneManager = PaneManager.getInstance();
        borderPane = paneManager.getBorderPane();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/enigma/TrophyPage.fxml"));
        fxmlLoader.setController(this);

        try {
            Node root = fxmlLoader.load();
            borderPane.setCenter(root);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        goingBack.setOnMouseClicked(mouseEvent -> back());
        lookUpTrophies();
    }

    private void back(){
        Node[] home = paneManager.getHomePage();
        borderPane.setTop(home[0]);
        borderPane.setCenter(home[1]);
        borderPane.setLeft(home[2]);
        borderPane.setRight(home[3]);
    }

    private void lookUpTrophies(){
        Trophy trophy = new Trophy();

        if (trophy.getCipherTrophy() >= 20){
            cipherTrophy.setDisable(false);
            cipherImage.setEffect(null);
        }

        if (trophy.getKeyTrophy() >= 20){
            keyTrophy.setDisable(false);
            keyImage.setEffect(null);
        }

        if (trophy.getDecipherTrophy() >= 20){
            decipherTrophy.setDisable(false);
            decipherImage.setEffect(null);
        }
    }


}
