package com.example.enigma;

import com.example.enigma.Model.SignUpLogic;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SignUpController {

    private final BorderPane borderPane;
    private final Node center;
    private final Label errorLabel;

    public SignUpController(BorderPane borderPane, Node center, Label errorLabel){
        this.center = center;
        this.borderPane = borderPane;
        this.errorLabel = errorLabel;
    }

    public void registerUser(TextField userNameSign, TextField passWordSign, TextField secretCode) {
        boolean validUserName = userNameValidator(userNameSign.getText());

        int passWordLength = passWordSign.getLength();
        if (!userNameSign.getText().isEmpty() && !passWordSign.getText().isEmpty() && validUserName
            && !secretCode.getText().isEmpty() && passWordLength >= 4){
            try {
                new SignUpLogic(userNameSign.getText(), passWordSign.getText(), secretCode.getText());
                errorLabel.setStyle("-fx-text-fill: White");
                timer(errorLabel, "Account created!", center);
            } catch (IOException e){
                errorLabel.setText("Username already exists");
                userNameSign.setText("");
                passWordSign.setText("");
            }
        } else if (userNameSign.getText().isEmpty()){
            errorLabel.setText("Please provide username");
        } else if (passWordSign.getText().isEmpty()){
            errorLabel.setText("Please provide password");
        } else if (secretCode.getText().isEmpty()){
            errorLabel.setText("Please provide a pokemon name");
        } else if (!validUserName) {
            errorLabel.setText("Username can contain only letters and numbers");
        } else {
            errorLabel.setText("Password at least of length four");
        }

    }

    public void timer(Label infoLabel, String info, Node center){
        int timeDuration = 1000;

        Task<Void> timerTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(timeDuration);
                } catch (InterruptedException e) {
                    System.out.println("Timer interrupted!");
                }
                return null;
            }
        };

        timerTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                borderPane.setCenter(center);
            });
        });

        infoLabel.setText(info);
        new Thread(timerTask).start();
    }

    private boolean userNameValidator(String text){
        return text.matches("[a-zA-Z0-9]+");
    }
}
