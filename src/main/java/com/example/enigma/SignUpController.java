package com.example.enigma;

import com.example.enigma.Model.SignUpLogic;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SignUpController {

    private final PaneManager paneManager;
    private final BorderPane borderPane;

    public SignUpController(PaneManager paneManager, BorderPane borderPane){
        this.paneManager = paneManager;
        this.borderPane = borderPane;
    }

    public void registerUser(TextField userNameSign, TextField passWordSign, Label errorLabel) {

        if (!userNameSign.getText().isEmpty() && !passWordSign.getText().isEmpty()){
            try {
                new SignUpLogic(userNameSign.getText(), passWordSign.getText());
                Node node = paneManager.previous();
                borderPane.setCenter(node);
            } catch (IOException e){
                errorLabel.setText("Username already exists");
                userNameSign.setText("");
                passWordSign.setText("");
            }
        } else if (userNameSign.getText().isEmpty()){
            errorLabel.setText("Please provide username");
        } else {
            errorLabel.setText("Please provide password");
        }

    }
}
