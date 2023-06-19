package com.example.enigma;

import com.example.enigma.Model.LogInLogic;
import com.example.enigma.Model.PaneManager;
import com.example.enigma.Model.SignUpLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class LogInController {

    @FXML TextField userNameField;
    @FXML TextField passWordField;
    @FXML BorderPane borderPane;

    @FXML TextField userNameSign;
    @FXML TextField passWordSign;

    private LogInLogic logInLogic;
    private final PaneManager paneManager;

    public LogInController() {

        paneManager = PaneManager.getInstance();

    }

    public void logIn(){
        logInLogic = new LogInLogic(userNameField.getText(), passWordField.getText());

        if (logInLogic.isAuthorizedUser()){
            System.out.println("Logged In");
        }
        else {
            System.out.println("No access");
        }
    }

    public void signUpPage() throws IOException {
        paneManager.next(borderPane.getCenter());
        Parent node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        borderPane.setCenter(node);

    }

    public void logInPage() {
        Node node = paneManager.previous();
        borderPane.setCenter(node);
    }

    public void registerUser() {
        new SignUpLogic(userNameSign.getText(), passWordSign.getText());
        logInPage();

    }

}
