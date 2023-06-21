package com.example.enigma;


import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;

import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.Objects;

public class MainPageController {

    private final PaneManager paneManager;
    private final BorderPane borderPane;

    public MainPageController(BorderPane borderPane) throws IOException {
        paneManager = PaneManager.getInstance();
        this.borderPane = borderPane;

        Parent center = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPage.fxml")));
        Parent top = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainPageBar.fxml")));

        borderPane.setCenter(center);
        borderPane.setTop(top);
    }

    public void logOut() {
        BorderPane node = (BorderPane) paneManager.firstElement();
        borderPane.setTop(node.getTop());
        borderPane.setCenter(node.getCenter());
    }

    public void account(){

    }

    public void achievement(){

    }
}
