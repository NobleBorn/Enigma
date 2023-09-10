package com.example.enigma.Controller;

import com.example.enigma.Model.Encryption.Cipher;
import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Trophy;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * The `CipherController` class handles the encryption functionality and manages the cipher page.
 * It allows users to enter text and a cipher key, perform encryption, copy/paste text, and control the virtual keyboard.
 * @see IEncodable
 * @see ITimer
 * @author Mojtaba Alizade
 */
public class CipherController implements IEncodable, ITimer {

    @FXML ImageView backButton;
    @FXML TextArea cipherText;
    @FXML TextField cipherKey;
    @FXML Button cipherTextButton;
    @FXML Label errorMessage;
    @FXML Circle setLock;
    @FXML Button cleanButton;
    @FXML Button pasteButton;
    @FXML Button copyButton;
    @FXML Label infoLabel;
    @FXML BorderPane borderPane;

    @FXML Button qButton;
    @FXML Button wButton;
    @FXML Button eButton;
    @FXML Button rButton;
    @FXML Button tButton;
    @FXML Button yButton;
    @FXML Button uButton;
    @FXML Button iButton;
    @FXML Button oButton;
    @FXML Button pButton;
    @FXML Button clearButton;
    @FXML Button aButton;
    @FXML Button sButton;
    @FXML Button dButton;
    @FXML Button fButton;
    @FXML Button gButton;
    @FXML Button hButton;
    @FXML Button jButton;
    @FXML Button kButton;
    @FXML Button lButton;
    @FXML Button apostropheButton;
    @FXML Button enterButton;
    @FXML Button zButton;
    @FXML Button xButton;
    @FXML Button cButton;
    @FXML Button vButton;
    @FXML Button bButton;
    @FXML Button nButton;
    @FXML Button mButton;
    @FXML Button commaButton;
    @FXML Button dotButton;
    @FXML Button spaceButton;
    @FXML Button lockButton;
    @FXML Button hideButton;
    @FXML Button showButton;


    private Stage stage;
    private Scene scene;
    private final PaneManager paneManager;
    private final CopyPaste copyPaste;
    private final HashMap<KeyCode, Button> buttons = new HashMap<>();
    private boolean lockOff = false;
    private final String[] operation = {"copy", "paste"};
    private String currentOperation;
    private final Rectangle2D bounds;

    /**
     * Constructs a CipherController object and initializes the cipher page of the Enigma application.
     *
     * @param event The action event that triggered the cipher page.
     */
    public CipherController(ActionEvent event){
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        paneManager = PaneManager.getInstance();
        copyPaste = CopyPaste.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/enigma/CipherPage.fxml"));
        fxmlLoader.setController(this);

        try {
            Parent root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            initializeCipherPage();
            populateButtons();
            buttonActions();

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setupKeyHandlers();
    }

    private void initializeCipherPage(){
        borderPane.setPrefWidth(bounds.getWidth());
        borderPane.setPrefHeight(bounds.getHeight());
        cipherText.setPrefWidth(bounds.getWidth() * 0.72);
        copyButton.setPrefWidth(bounds.getWidth() * 0.053);
        copyButton.setLayoutX(bounds.getWidth() * 0.7);
        pasteButton.setPrefWidth(bounds.getWidth() * 0.053);
        pasteButton.setLayoutX(bounds.getWidth() * 0.76);
        cleanButton.setPrefWidth(bounds.getWidth() * 0.053);
        cleanButton.setLayoutX(bounds.getWidth() * 0.82);
        infoLabel.setLayoutX(bounds.getWidth() * 0.426);

        SoundController soundController = new SoundController();
        soundController.sound(copyButton);
        soundController.sound(pasteButton);
        soundController.sound(cleanButton);
    }

    /**
     * Start of the encryption process when the cipher text button is clicked.
     */
    private void cipher(){
        TextValidator textValidator = new TextValidator(cipherText.getText(), cipherKey.getText());
        textValidator.validator(errorMessage, this);
    }

    /**
     * Navigates back to the previous page.
     *
     * @param event The mouse event that triggered the back action.
     */
    private void back(MouseEvent event){
        Parent root = paneManager.getBorderPane();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Populates the hashmap with button objects and their associated key codes.
     */
    private void populateButtons(){
        buttons.put(KeyCode.SPACE, spaceButton);
        buttons.put(KeyCode.Q, qButton);
        buttons.put(KeyCode.W, wButton);
        buttons.put(KeyCode.E, eButton);
        buttons.put(KeyCode.R, rButton);
        buttons.put(KeyCode.T, tButton);
        buttons.put(KeyCode.Y, yButton);
        buttons.put(KeyCode.U, uButton);
        buttons.put(KeyCode.I, iButton);
        buttons.put(KeyCode.O, oButton);
        buttons.put(KeyCode.P, pButton);
        buttons.put(KeyCode.BACK_SPACE, clearButton);
        buttons.put(KeyCode.A, aButton);
        buttons.put(KeyCode.S, sButton);
        buttons.put(KeyCode.D, dButton);
        buttons.put(KeyCode.F, fButton);
        buttons.put(KeyCode.G, gButton);
        buttons.put(KeyCode.H, hButton);
        buttons.put(KeyCode.J, jButton);
        buttons.put(KeyCode.K, kButton);
        buttons.put(KeyCode.L, lButton);
        buttons.put(KeyCode.BACK_SLASH, apostropheButton);
        buttons.put(KeyCode.ENTER, enterButton);
        buttons.put(KeyCode.Z, zButton);
        buttons.put(KeyCode.X, xButton);
        buttons.put(KeyCode.C, cButton);
        buttons.put(KeyCode.V, vButton);
        buttons.put(KeyCode.B, bButton);
        buttons.put(KeyCode.N, nButton);
        buttons.put(KeyCode.M, mButton);
        buttons.put(KeyCode.COMMA, commaButton);
        buttons.put(KeyCode.PERIOD, dotButton);
        buttons.put(KeyCode.CAPS, lockButton);
        buttons.put(KeyCode.ESCAPE, hideButton);
    }

    /**
     * Sets the actions for each button in the virtual keyboard.
     */
    private void buttonActions(){
        for (Button button : buttons.values()){
            setButtons(button);
        }

        backButton.setOnMouseClicked(this::back);
        cipherTextButton.setOnAction(actionEvent -> cipher());
        showButton.setOnAction(actionEvent -> showKeyBoard());
        cleanButton.setOnAction(actionEvent -> removeText());
        copyButton.setOnAction(actionEvent -> copy());
        pasteButton.setOnAction(actionEvent -> paste());
    }

    /**
     * Sets the actions for some special buttons.
     *
     * @param button The button to set the action for.
     */
    private void setButtons(Button button){
        if (button != clearButton && button != enterButton && button != lockButton && button != hideButton
                && button != spaceButton){
            button.setOnAction(actionEvent -> keyBoard(button.getText()));
        } else if (button == clearButton){
            button.setOnAction(actionEvent -> clearText());
        } else if (button == enterButton){
            button.setOnAction(actionEvent -> cipher());
        } else if (button == lockButton){
            button.setOnAction(actionEvent -> capLock());
        } else if (button == spaceButton){
            button.setOnAction(actionEvent -> keyBoard(" "));
        }
        else {
            hideButton.setOnAction(actionEvent -> hideKeyBoard());
        }
    }

    /**
     * Handles the key press event for the virtual keyboard.
     *
     * @param buttonText The text associated with the pressed key.
     */
    private void keyBoard(String buttonText){
        String text = cipherText.getText();
        if (lockOff){
            cipherText.setText(text+buttonText);
        } else {
            cipherText.setText(text+buttonText.toLowerCase());
        }
    }

    /**
     * Clears the last character from the cipher text.
     */
    private void clearText(){
        String text = cipherText.getText();
        String newText = text.substring(0, text.length()-1);
        cipherText.setText(newText);
    }

    /**
     * Hides the virtual keyboard by hiding all buttons except the show button.
     */
    private void hideKeyBoard(){
        for (Button button : buttons.values()){
            button.setVisible(false);
        }
        showButton.setVisible(true);
    }

    /**
     * Shows the virtual keyboard by making all buttons visible.
     */
    private void showKeyBoard(){
        for (Button button : buttons.values()){
            button.setVisible(true);
        }
        showButton.setVisible(false);
    }

    /**
     * Toggles the capital lock state by changing the visibility of the lock indicator.
     */
    private void capLock(){
        if (lockOff){
            setLock.setVisible(false);
            lockOff = false;
        } else {
            setLock.setVisible(true);
            lockOff = true;
        }
    }

    /**
     * Sets up the key handlers for the cipher text and cipher key fields.
     */
    private void setupKeyHandlers() {
        cipherText.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        cipherText.setOnKeyReleased(this::handleKeyReleased);

        cipherKey.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        cipherKey.setOnKeyReleased(this::handleKeyReleased);
    }

    /**
     * Handles the key press event for the virtual keyboard buttons.
     *
     * @param event The key event that was triggered.
     */
    private void handleKeyPressed(KeyEvent event) {
        Button pressed = buttons.get(event.getCode());
        if (pressed != null) {
            if (pressed == enterButton || pressed == lockButton) {
                event.consume();
                pressed.fire();
            }
            pressed.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), true);
        }
    }

    /**
     * Handles the key release event for the virtual keyboard buttons.
     *
     * @param event The key event that was triggered.
     */
    private void handleKeyReleased(KeyEvent event) {
        Button pressed = buttons.get(event.getCode());
        if (pressed != null) {
            pressed.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), false);
        }
    }

    /**
     * Writes the encoded text on the text area
     * Adds to user trophy score
     */
    @Override
    public void ciphering() {
        errorMessage.setText("");
        Cipher cipher = new Cipher(cipherText.getText(), cipherKey.getText(),
                "src/main/resources/com/example/enigma/keys.csv");
        cipherText.setText(cipher.getCodedText());

        addToUserTrophy();
    }

    /**
     * Removes the text from the text area.
     */
    private void removeText(){
        cipherText.setText("");
    }

    /**
     * Copies the cipher text to the clipboard.
     */
    private void copy(){
        currentOperation = operation[0];
        copyPaste.copyText(cipherText.getText());
        copyPaste.countTime(this);
    }

    /**
     * Pastes the text from the clipboard to the cipher text area.
     */
    private void paste(){
        currentOperation = operation[1];
        cipherText.setText(copyPaste.pasteText());
        copyPaste.countTime(this);

    }

    /**
     * Operations during timer
     */
    @Override
    public void duringTimer() {
        if (currentOperation.equals(operation[0])){
            infoLabel.setText("Copied");
            pasteButton.setDisable(true);
        } else {
            infoLabel.setText("Pasted");
            copyButton.setDisable(true);
        }
    }

    /**
     * Operations after timer
     */
    @Override
    public void afterTimer() {
        infoLabel.setText("");

        if (currentOperation.equals(operation[0])){
            pasteButton.setDisable(false);
        } else {
            copyButton.setDisable(false);
        }
    }

    /**
     * Raise user trophy score
     */
    private void addToUserTrophy(){
        Trophy trophy = new Trophy("src/main/resources/com/example/enigma/trophy.csv");

        if (trophy.getCipherTrophy() < 20){
            trophy.updateTrophy(1);
        }

        if (trophy.getKeyTrophy() < 20){
            trophy.updateTrophy(2);
        }
    }
}
