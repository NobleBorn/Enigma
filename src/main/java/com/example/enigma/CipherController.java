package com.example.enigma;

import com.example.enigma.Model.Cipher;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class CipherController implements IEncodable{

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

    public CipherController(ActionEvent event){
        paneManager = PaneManager.getInstance();
        copyPaste = CopyPaste.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CipherPage.fxml"));
        fxmlLoader.setController(this);

        try {
            Parent root = fxmlLoader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);

            populateButtons();
            buttonActions();

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setupKeyHandlers();
    }

    private void cipher(){
        TextValidator textValidator = new TextValidator(cipherText.getText(), cipherKey.getText());
        textValidator.validator(errorMessage, this);
    }

    private void back(MouseEvent event){
        Parent root = (Parent) paneManager.previous();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = root.getScene();
        stage.setScene(scene);
        stage.show();
    }

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

    private void keyBoard(String buttonText){
        String text = cipherText.getText();
        if (lockOff){
            cipherText.setText(text+buttonText);
        } else {
            cipherText.setText(text+buttonText.toLowerCase());
        }
    }

    private void clearText(){
        String text = cipherText.getText();
        String newText = text.substring(0, text.length()-1);
        cipherText.setText(newText);
    }

    private void hideKeyBoard(){
        for (Button button : buttons.values()){
            button.setVisible(false);
        }
        showButton.setVisible(true);
    }

    private void showKeyBoard(){
        for (Button button : buttons.values()){
            button.setVisible(true);
        }
        showButton.setVisible(false);
    }

    private void capLock(){
        if (lockOff){
            setLock.setVisible(false);
            lockOff = false;
        } else {
            setLock.setVisible(true);
            lockOff = true;
        }
    }

    private void setupKeyHandlers() {
        cipherText.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        cipherText.setOnKeyReleased(this::handleKeyReleased);

        cipherKey.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPressed);
        cipherKey.setOnKeyReleased(this::handleKeyReleased);
    }

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

    private void handleKeyReleased(KeyEvent event) {
        Button pressed = buttons.get(event.getCode());
        if (pressed != null) {
            pressed.pseudoClassStateChanged(PseudoClass.getPseudoClass("pressed"), false);
        }
    }


    @Override
    public void ciphering() {
        errorMessage.setText("");
        Cipher cipher = new Cipher(cipherText.getText(), cipherKey.getText());
        cipherText.setText(cipher.getCodedText());
    }

    private void removeText(){
        cipherText.setText("");
    }

    private void copy(){
        copyPaste.copyText(cipherText.getText());
        copyPaste.timer(infoLabel,"Copied", pasteButton);
    }

    private void paste(){
        cipherText.setText(copyPaste.pasteText());
        copyPaste.timer(infoLabel,"Pasted", copyButton);

    }
}
