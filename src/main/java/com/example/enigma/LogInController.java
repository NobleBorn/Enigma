package com.example.enigma;

import com.example.enigma.Model.LogInLogic;
import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    @FXML TextField pokemonCode;
    @FXML Label errorLabel;
    @FXML Button signUpButton;

    private final PaneManager paneManager;
    private final User current = User.getInstance();
    private Node center;


    public LogInController() {
        paneManager = PaneManager.getInstance();
    }

    public void logIn() {
        if (!userNameField.getText().isEmpty() && !passWordField.getText().isEmpty()){
            LogInLogic logInLogic = new LogInLogic(userNameField.getText(), passWordField.getText());
            if (logInLogic.isAuthorizedUser()){
                current.currentUser(userNameField.getText());
                errorText.setText("");
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
            }
        } else if (userNameField.getText().isEmpty()){
            errorText.setText("Please provide username");
        } else {
            errorText.setText("Please provide password");
        }
    }

    public void signUpPage() throws IOException {
        center = borderPane.getCenter();
        paneManager.next(borderPane);
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        fxmlLoader.setController(this);
        Parent node = fxmlLoader.load();
        signUpButton.setOnAction(actionEvent -> registerUser());
        borderPane.setCenter(node);
    }

    public void logInPage() {
        borderPane.setCenter(center);
    }

    public void registerUser() {
        BorderPane border = (BorderPane) paneManager.peekPrevious();
        SignUpController signUpController = new SignUpController(border, center, errorLabel);
        signUpController.registerUser(userNameSign, passWordSign, pokemonCode);
    }

}
