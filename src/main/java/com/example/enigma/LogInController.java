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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
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
    private final HashMap<String, Character> secretMessage = new HashMap<>();


    public LogInController() {
        paneManager = PaneManager.getInstance();
        populateMap();
    }

    public void logIn() {
        if (!userNameField.getText().isEmpty() && !passWordField.getText().isEmpty()){
            LogInLogic logInLogic = new LogInLogic(userNameField.getText(), passWordField.getText());
            if (logInLogic.isAuthorizedUser()){
                Node[] startPage = {borderPane.getTop(), borderPane.getCenter(), borderPane.getLeft(),
                        borderPane.getRight()};
                paneManager.setStartPage(startPage);

                current.currentUser(userNameField.getText());
                errorText.setText("");
                userNameField.setText("");
                passWordField.setText("");
                paneManager.setBorderPane(borderPane);

                try {
                    new MainPageController();
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
        paneManager.setBorderPane(borderPane);
        paneManager.saveCurrentNodes(borderPane.getCenter());
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("SignUp.fxml")));
        fxmlLoader.setController(this);
        Parent node = fxmlLoader.load();
        signUpButton.setOnAction(actionEvent -> registerUser());
        borderPane.setCenter(node);
    }

    public void logInPage() {
        borderPane.setCenter(paneManager.getPreviousNodes());
    }

    public void registerUser() {
        SignUpController signUpController = new SignUpController(errorLabel);
        signUpController.registerUser(userNameSign, passWordSign, pokemonCode);
    }

    public void changeMessage(MouseEvent event){
        Label sourceLabel = (Label) event.getTarget();
        String id = sourceLabel.getId();
        if (secretMessage.get(id) != null) sourceLabel.setText(String.valueOf(secretMessage.get(id)));
    }

    private void populateMap(){
        char[] letters = "WE KEEPYOURSECRETSAFENobleBorn".toCharArray();
        for (int i = 0; i < letters.length; i++){
            secretMessage.put("message"+(i+1), letters[i]);
        }
    }

}
