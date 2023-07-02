package com.example.enigma;

import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Objects;

public class KeysPageController {

    @FXML ScrollPane scrollPane;
    @FXML VBox keyVBox;
    @FXML Pane secretCodePane;
    @FXML TextField secretPass;
    @FXML Button submitButton;

    private final PaneManager paneManager;
    private final User user;

    public KeysPageController(BorderPane borderPane){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KeysPage.fxml")));
        loader.setController(this);
        paneManager = PaneManager.getInstance();
        user = User.getInstance();

        try {
            Parent node = loader.load();
            borderPane.setCenter(node);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        submitButton.setOnAction(actionEvent -> submit());
        header();
        keys();
    }

    private void keys(){
        for (int i = 10; i >= 0; i--){
            HBox hBox = new HBox();
            hBox.setId("hBox");

            Label key = new Label();
            key.setText(String.valueOf(i));

            Label creation = new Label();
            creation.setText("2023-07-01");

            Button button = new Button("Delete");

            hBox.getChildren().addAll(key, creation, button);
            keyVBox.getChildren().add(hBox);
        }

        scrollPane.setContent(keyVBox);

    }

    private void header(){
        HBox hBox = new HBox();
        hBox.setId("hBox");

        Label key = new Label();
        key.setText("Key");

        Label creation = new Label();
        creation.setText("Date");

        Button button = new Button("Delete");
        button.setDisable(true);
        button.setVisible(false);

        hBox.getChildren().addAll(key, creation, button);
        keyVBox.getChildren().add(hBox);
    }

    private void submit(){
        secretCodePane.setDisable(true);
        secretCodePane.setVisible(false);
    }
}
