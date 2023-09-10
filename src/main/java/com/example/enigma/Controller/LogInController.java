package com.example.enigma.Controller;

import com.example.enigma.Model.Client.LogInLogic;
import com.example.enigma.Model.Client.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * The LogInController class handles the login functionality and user interface events related to login, signup,
 * and user actions in the Enigma application.
 * @author Mojtaba Alizade
 */
public class LogInController implements Initializable {

    @FXML Button logInMenuButton;
    @FXML Button signUpMenuButton;
    @FXML TextField userNameField;
    @FXML PasswordField passWordField;
    @FXML BorderPane borderPane;
    @FXML Label errorText;
    @FXML Button logInButton;

    @FXML TextField userNameSign;
    @FXML PasswordField passWordSign;
    @FXML PasswordField pokemonCode;
    @FXML Label errorLabel;
    @FXML Button signUpButton;

    @FXML Button trophyButton;

    private final PaneManager paneManager;
    private final User current = User.getInstance();
    private final HashMap<String, Character> secretMessage = new HashMap<>();
    private final Set<String> secretMessageIDs = Set.of("message12", "message13", "message14", "message15",
            "message16", "message17");
    private final Rectangle2D bounds;
    private final SoundController soundController = new SoundController();
    private final SecretMessage secret = new SecretMessage();

    /**
     * Constructs a LogInController object and initializes the PaneManager instance.
     */
    public LogInController() {
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        paneManager = PaneManager.getInstance();
        String[] filePaths = {
                "src/main/resources/com/example/enigma/users.csv",
                "src/main/resources/com/example/enigma/rememberUser.csv",
                "src/main/resources/com/example/enigma/keys.csv"
        };
        current.init(filePaths);
        secret.populateMap();
    }

    public void restrict(){
        restrictSpaces(passWordField);
    }

    /**
     * Handles the login action when the login button is clicked.
     * It verifies the entered username and password, and if valid, sets up the main page.
     */
    public void logIn() {
        if (!userNameField.getText().isEmpty() && !passWordField.getText().isEmpty()){

            String name = userNameField.getText().trim();
            String pass = passWordField.getText().trim();

            LogInLogic logInLogic = new LogInLogic(name, pass,
                    "src/main/resources/com/example/enigma/users.csv");

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

    /**
     * Handles the signup page transition when the signup button is clicked.
     *
     */
    public void signUpPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/enigma/SignUp.fxml")));
        fxmlLoader.setController(this);

        SignUpPage page = new SignUpPage();
        page.signUpPage(fxmlLoader);
    }

    /**
     * Restricts the space character in the field
     * @param passwordField The password field being restricted
     */
    private void restrictSpaces(PasswordField passwordField) {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                passwordField.setText(newValue.replaceAll(" ", ""));
            }
        });
    }

    /**
     * Handles the transition back to the login page.
     */
    public void logInPage() {
        borderPane.setCenter(paneManager.getPreviousNodes());
        if (!logInMenuButton.isDisabled()) logInMenuButton.setDisable(true);
        signUpMenuButton.setDisable(false);
    }


    /**
     * Changes the message text and style when a secret message label is clicked.
     *
     * @param event The mouse event triggered by clicking a secret message label.
     */
    public void changeMessage(MouseEvent event){
        secret.changeMessage(event);
    }


    /**
     * Handles the trophies action when the trophy button is clicked.
     * It transitions to the trophy page.
     */
    public void trophies() {
        paneManager.setBorderPane(borderPane);
        paneManager.saveCurrentNodes(borderPane.getTop());
        paneManager.saveCurrentNodes(borderPane.getCenter());
        paneManager.saveCurrentNodes(borderPane.getLeft());
        paneManager.saveCurrentNodes(borderPane.getRight());

        nullify();
        new TrophyController();
    }

    /**
     * Nullifies the left and right panes of the border pane.
     */
    private void nullify() {
        borderPane.setRight(null);
        borderPane.setLeft(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInMenuButton.setDisable(true);
        userNameField.setLayoutX(bounds.getWidth() * 0.095);
        passWordField.setLayoutX(bounds.getWidth() * 0.095);
        logInButton.setLayoutX(bounds.getWidth() * 0.17);
        logInButton.setLayoutY(passWordField.getLayoutY() + 80);
        errorText.setLayoutX(bounds.getWidth() * 0.095);
        logInMenuButton.setLayoutX(bounds.getWidth() * 0.83);
        signUpMenuButton.setLayoutX(bounds.getWidth() * 0.92);

        soundController.sound(logInButton);
        soundController.sound(logInMenuButton);
        soundController.sound(signUpMenuButton);
        soundController.sound(trophyButton);
    }

    /**
     * Helper class for the SignUp page
     */
    private class SignUpPage {
        /**
         * Handles the signup page transition when the signup button is clicked.
         *
         */
        private void signUpPage(FXMLLoader fxmlLoader) {
            errorText.setText("");
            userNameField.setText("");
            passWordField.setText("");

            paneManager.setBorderPane(borderPane);
            paneManager.saveCurrentNodes(borderPane.getCenter());

            try {
                Parent node = fxmlLoader.load();
                initializeSigUpPage();
                signUpButton.setOnAction(actionEvent -> registerUser());
                borderPane.setCenter(node);
            } catch (IOException e){
                System.out.println(e.getMessage());
            }

            restrictSpaces(passWordSign);
            restrictSpaces(pokemonCode);
        }

        private void initializeSigUpPage(){
            userNameSign.setLayoutX(bounds.getWidth() * 0.095);
            passWordSign.setLayoutX(bounds.getWidth() * 0.095);
            pokemonCode.setLayoutX(bounds.getWidth() * 0.095);
            signUpButton.setLayoutX(bounds.getWidth() * 0.17);
            signUpButton.setLayoutY(pokemonCode.getLayoutY() + 70);
            errorLabel.setLayoutX(bounds.getWidth() * 0.03);

            soundController.sound(signUpButton);
            logInMenuButton.setDisable(false);
            signUpMenuButton.setDisable(true);
        }

        /**
         * Registers a new user when the signup button is clicked.
         */
        private void registerUser() {
            SignUpController signUpController = new SignUpController(errorLabel);
            signUpController.registerUser(userNameSign, passWordSign, pokemonCode);
        }
    }

    /**
     * Helper class for the display of the secret message
     */
    private class SecretMessage {
        /**
         * Changes the message text and style when a secret message label is clicked.
         *
         * @param event The mouse event triggered by clicking a secret message label.
         */
        public void changeMessage(MouseEvent event){
            Label sourceLabel = (Label) event.getTarget();
            String id = sourceLabel.getId();
            if (secretMessageIDs.contains(id)) changeStyle(sourceLabel);

            if (secretMessage.get(id) != null) sourceLabel.setText(String.valueOf(secretMessage.get(id)));
        }

        /**
         * Populates the secretMessage map with the corresponding secret characters.
         */
        private void populateMap(){
            char[] letters = "WE KEEPYOURSECRETSAFENobleBorn".toCharArray();
            for (int i = 0; i < letters.length; i++){
                secretMessage.put("message"+(i+1), letters[i]);
            }
        }

        /**
         * Changes the style of the secret message label.
         *
         * @param label The label to change the style for.
         */
        private void changeStyle(Label label) {
            label.setStyle("-fx-text-fill: #ff0d0d; -fx-effect: dropshadow(gaussian, #c35252, 10, 0, 0, 0)");
        }
    }
}
