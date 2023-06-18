package com.example.enigma;

import com.example.enigma.Model.LogInLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LogInController {

    @FXML TextField userNameField;
    @FXML TextField passWordField;
    @FXML BorderPane borderPane;

    private LogInLogic logInLogic;


    public LogInController() {
        if (borderPane == null)
            System.out.println("null");
    }

    public void logIn(){
        logInLogic = new LogInLogic(userNameField.getText(), passWordField.getText());

        if (logInLogic.isAuthorizedUser()){
            System.out.println("baba");
        }
        else {
            System.out.println("baby");
        }
    }

    public void signUp() throws IOException {
        Parent node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        borderPane.setCenter(node);

    }

    public void logInPage() throws IOException {
        Parent node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LogIn.fxml")));
        borderPane.setCenter(node);
    }

}
