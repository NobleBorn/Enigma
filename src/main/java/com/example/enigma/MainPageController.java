package com.example.enigma;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


import java.io.IOException;

public class MainPageController {

    @FXML Button logOutButton;
    @FXML Button cipherButton;
    @FXML Button decipherButton;

    private final PaneManager paneManager;
    private final BorderPane borderPane;
    private final Node topBar;
    private final Node centerPart;

    public MainPageController(BorderPane borderPane) throws IOException {
        paneManager = PaneManager.getInstance();
        this.borderPane = borderPane;
        this.topBar = borderPane.getTop();
        this.centerPart = borderPane.getCenter();

        FXMLLoader topBar = new FXMLLoader(getClass().getResource("MainPageBar.fxml"));
        FXMLLoader centerPart = new FXMLLoader(getClass().getResource("MainPage.fxml"));
        topBar.setController(this);
        centerPart.setController(this);
        Parent top;
        Parent center;
        try {
            top = topBar.load();
            center = centerPart.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        logOutButton.setOnAction(actionEvent -> logOut());
        cipherButton.setOnAction(this::cipher);

        borderPane.setCenter(center);
        borderPane.setTop(top);
    }

    public void logOut() {
        borderPane.setTop(topBar);
        borderPane.setCenter(centerPart);
    }

    public void account(){

    }

    public void achievement(){

    }

    public void cipher(ActionEvent event){
        paneManager.next(borderPane);
        new CipherController(event);
    }
}
