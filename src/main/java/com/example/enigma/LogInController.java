package com.example.enigma;

import com.example.enigma.Model.LogInLogic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class LogInController {

    @FXML TextField userNameField;
    @FXML TextField passWordField;
    @FXML BorderPane borderPane;
    @FXML Label errorText;

    @FXML TextField userNameSign;
    @FXML TextField passWordSign;
    @FXML Label errorLabel;

    private final PaneManager paneManager;

    public LogInController() {
        paneManager = PaneManager.getInstance();
    }

    public void logIn() {

        if (!userNameField.getText().equals("") && !passWordField.getText().equals("")){
            LogInLogic logInLogic = new LogInLogic(userNameField.getText(), passWordField.getText());
            if (logInLogic.isAuthorizedUser()){
                userNameField.setText("");
                passWordField.setText("");
                paneManager.next(borderPane);

                try {
                    new MainPageController(borderPane);
                } catch (IOException e){
                    System.out.println("Can not load the main page " + e.getMessage());
                }


            }
            else {
                errorText.setText("Wrong username or password");
                userNameField.setText("");
                passWordField.setText("");
            }
        } else if (userNameField.getText().equals("")){
            errorText.setText("Please provide username");
        } else {
            errorText.setText("Please provide password");
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
        SignUpController signUpController = new SignUpController(paneManager, borderPane);
        signUpController.registerUser(userNameSign, passWordSign, errorLabel);
    }


}
