package com.example.enigma.Controller;

import com.example.enigma.Model.Client.LogIn.LogInLogic;
import com.example.enigma.Model.Client.User;
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
import java.util.Set;

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

    @FXML Button trophyButton;


    private final PaneManager paneManager;
    private final User current = User.getInstance();
    private final HashMap<String, Character> secretMessage = new HashMap<>();
    private final Set<String> secretMessageIDs = Set.of("message12", "message13", "message14", "message15",
            "message16", "message17");


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
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/enigma/SignUp.fxml")));
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
        if (secretMessageIDs.contains(id)) changeStyle(sourceLabel);

        if (secretMessage.get(id) != null) sourceLabel.setText(String.valueOf(secretMessage.get(id)));
    }

    private void populateMap(){
        char[] letters = "WE KEEPYOURSECRETSAFENobleBorn".toCharArray();
        for (int i = 0; i < letters.length; i++){
            secretMessage.put("message"+(i+1), letters[i]);
        }
    }

    private void changeStyle(Label label){
        label.setStyle("-fx-text-fill: #ff0d0d; -fx-effect: dropshadow(gaussian, #c35252, 10, 0, 0, 0)");
    }

    public void trophies(){
        paneManager.setBorderPane(borderPane);
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());

        nullify();
        new TrophyController();
    }

    private void nullify(){
        borderPane.setRight(null);
        borderPane.setLeft(null);
    }

}
