package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.IChangeable;
import com.example.enigma.Model.Client.User;
import com.example.enigma.Model.ModifyFiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The `KeysPageController` class is responsible for managing the keys page and related operations.
 * It handles displaying and deleting user keys, validating the secret code, and remembering the user.
 * @see IChangeable
 * @author Mojtaba Alizade
 */
public class KeysPageController implements IChangeable {

    @FXML ScrollPane scrollPane;
    @FXML VBox keyVBox;
    @FXML Pane secretCodePane;
    @FXML TextField secretPass;
    @FXML Button submitButton;
    @FXML ImageView backPointer;
    @FXML Label errorInfo;
    @FXML CheckBox rememberMe;

    private final PaneManager paneManager;
    private final User user;
    private final BorderPane borderPane;
    private final String[] operation = {"delete", "rememberUser"};
    private String currentOperation;
    private ModifyFiles modifyRemember;
    private String keyText;

    /**
     * Constructs a KeysPageController object and initializes the keys page of the Enigma application.
     */
    public KeysPageController(){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource("/com/example/enigma/KeysPage.fxml")));
        loader.setController(this);
        paneManager = PaneManager.getInstance();
        user = User.getInstance();
        borderPane = paneManager.getBorderPane();

        try {
            Parent node = loader.load();
            borderPane.setCenter(node);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }


        user.currentUser(user.getName());
        if (user.isRemembered()){
            secretCodePane.setDisable(true);
            secretCodePane.setVisible(false);
        }

        submitButton.setOnAction(actionEvent -> submit());
        backPointer.setOnMouseClicked(mouseEvent -> back());
        keys();
    }

    /**
     * Loads and displays the user's keys.
     */
    private void keys(){
        header();

        user.newMap();
        user.keys();

        Map<String, String> userKeys = user.getUserKeys();
        int hBoxID = 0;

        for (String key : userKeys.keySet()){
            HBox hBox = new HBox();
            hBox.setId("hBox");

            Label keyValue = new Label();
            keyValue.setText(key);
            keyValue.setId(String.valueOf(hBoxID++));

            Label creation = new Label();
            creation.setText(userKeys.get(key));

            Button button = new Button("Delete");
            button.setOnAction(this::deleteKey);

            hBox.getChildren().addAll(keyValue, creation, button);
            keyVBox.getChildren().add(hBox);

        }

        scrollPane.setContent(keyVBox);
    }

    /**
     * Adds the header labels for key and creation date.
     */
    private void header(){
        HBox hBox = new HBox();
        hBox.setId("hBox");

        Label key = new Label();
        key.setText("Key");

        Label creation = new Label();
        creation.setText("Date");

        Button button = new Button("Delete");
        button.setDisable(true);
        button.setVisible(false);

        hBox.getChildren().addAll(key, creation, button);
        keyVBox.getChildren().clear();
        keyVBox.getChildren().add(hBox);
    }

    /**
     * Submits the secret code and handles the corresponding actions.
     */
    private void submit(){
        if (secretPass.getText().equals(user.getSecretCode()) && !secretPass.getText().isEmpty()){
            secretCodePane.setDisable(true);
            secretCodePane.setVisible(false);
            if (rememberMe.isSelected()){
                currentOperation = operation[1];
                modifyRemember = new ModifyFiles("src/main/resources/com/example/enigma/rememberUser.csv");
                List<CSVRecord> records = modifyRemember.readRecords();
                modifyRemember.modifyRecords(records, this);
            }
        } else if (secretPass.getText().isEmpty()) {
            errorInfo.setText("Provide the secret code");
        } else {
            errorInfo.setText("Incorrect code!");
        }
    }

    /**
     * Deletes a key from the user's keys.
     *
     * @param event The action event triggered by the delete button.
     */
    private void deleteKey(ActionEvent event){
        currentOperation = operation[0];
        keyText = getDeletedKeyText(event);

        ModifyFiles modifyFiles = new ModifyFiles("src/main/resources/com/example/enigma/keys.csv");
        List<CSVRecord> records = modifyFiles.readRecords();
        modifyFiles.modifyRecords(records, this);

        keys();
    }

    /**
     * Navigates back to the previous page.
     */
    private void back(){
        paneManager.removePrevious();

        Node nodeRight = paneManager.getPreviousNodes();
        Node nodeLeft = paneManager.getPreviousNodes();
        Node nodeCenter = paneManager.getPreviousNodes();
        Node nodeTop = paneManager.getPreviousNodes();
        borderPane.setTop(nodeTop);
        borderPane.setCenter(nodeCenter);
        borderPane.setLeft(nodeLeft);
        borderPane.setRight(nodeRight);
    }

    /**
     * Modifies records of CSV file based on which operation it is
     * @param records The list of CSV records to be modified.
     */
    @Override
    public void modify(List<CSVRecord> records) {
        if (currentOperation.equals(operation[0])){
            for (int i = 0; i < records.size(); i++) {
                CSVRecord record = records.get(i);
                if (record.get(0).equals(String.valueOf(user.getUserId())) && record.get(2).equals(keyText)) {
                    records.remove(record);
                }
            }
        } else {
            String[] newValues = {String.valueOf(user.getUserId()), String.valueOf(rememberMe.isSelected())};
            modifyRemember.newRecord(records, newValues, String.valueOf(user.getUserId()));
        }

    }

    /**
     * Retrieves the ID of the HBox containing the delete button for a key.
     *
     * @param event The action event triggered by the delete button.
     * @return The text of the key that is deleted
     */
    private String getDeletedKeyText(ActionEvent event){
        Button button = (Button) event.getSource();
        HBox hbox = (HBox) button.getParent();
        String keyText = "";

        for (Node child : hbox.getChildren()) {
            if (child.getId() != null){
                Label keyLabel = (Label) child;
                keyText = keyLabel.getText();
            }
        }

        return keyText;
    }
}
