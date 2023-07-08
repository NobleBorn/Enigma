package com.example.enigma.Controller;

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
    @FXML Button accountButton;
    @FXML Button keyButton;
    @FXML Button homeButton;

    private final PaneManager paneManager;
    private final BorderPane borderPane;

    public MainPageController() throws IOException {
        paneManager = PaneManager.getInstance();
        borderPane = paneManager.getBorderPane();

        Node[] startPage = paneManager.getStartPage();
        Node rightPart = startPage[3];
        Node leftPart = startPage[2];

        FXMLLoader topBar = new FXMLLoader(getClass().getResource("/com/example/enigma/MainPageBar.fxml"));
        FXMLLoader centerPart = new FXMLLoader(getClass().getResource("/com/example/enigma/MainPage.fxml"));
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
        decipherButton.setOnAction(this::deCipher);
        accountButton.setOnAction(actionEvent -> account());
        keyButton.setOnAction(actionEvent -> usersKeys());
        homeButton.setOnAction(actionEvent -> home());

        borderPane.setCenter(center);
        borderPane.setTop(top);
        borderPane.setRight(rightPart);
        borderPane.setLeft(leftPart);

        rightPart.setVisible(true);
        rightPart.setDisable(false);
        leftPart.setVisible(true);
        leftPart.setDisable(false);

        Node[] home = {borderPane.getTop(), borderPane.getCenter(), borderPane.getLeft(),
                borderPane.getRight()};
        paneManager.setHomePage(home);
    }

    public void logOut() {
        Node[] startPage = paneManager.getStartPage();

        if (borderPane.getRight() == null && borderPane.getLeft() == null) {
            borderPane.setRight(startPage[3]);
            borderPane.setLeft(startPage[2]);
        }

        borderPane.getRight().setVisible(false);
        borderPane.getRight().setDisable(true);

        borderPane.getLeft().setVisible(false);
        borderPane.getLeft().setDisable(true);

        borderPane.setTop(startPage[0]);
        borderPane.setCenter(startPage[1]);
    }

    public void home(){
        Node[] home = paneManager.getHomePage();
        borderPane.setTop(home[0]);
        borderPane.setCenter(home[1]);
        borderPane.setLeft(home[2]);
        borderPane.setRight(home[3]);
    }

    public void account() {
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());

        nullify();
        new AccountController();
    }

    public void usersKeys(){
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());

        nullify();
        new KeysPageController();
    }

    public void cipher(ActionEvent event){
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());
        new CipherController(event);
    }

    public void deCipher(ActionEvent event){
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());
        new DeCipherController(event);
    }

    private void nullify(){
        borderPane.setRight(null);
        borderPane.setLeft(null);
    }


}
