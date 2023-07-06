package com.example.enigma;

import com.example.enigma.Model.FileManager;
import com.example.enigma.Model.IChangable;
import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KeysPageController implements IChangable {

    @FXML ScrollPane scrollPane;
    @FXML VBox keyVBox;
    @FXML Pane secretCodePane;
    @FXML TextField secretPass;
    @FXML Button submitButton;
    @FXML ImageView backPointer;
    @FXML Label errorInfo;

    private final PaneManager paneManager;
    private final User user;
    private final BorderPane borderPane;

    public KeysPageController(){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KeysPage.fxml")));
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

        submitButton.setOnAction(actionEvent -> submit());
        backPointer.setOnMouseClicked(mouseEvent -> back());
        keys();
    }

    private void keys(){
        header();

        user.newMap();
        user.keys();
        HashMap<String, String> userKeys = user.getUserKeys();

        for (String key : userKeys.keySet()){
            HBox hBox = new HBox();
            hBox.setId("hBox");

            Label keyValue = new Label();
            keyValue.setText(key);

            Label creation = new Label();
            creation.setText(userKeys.get(key));

            Button button = new Button("Delete");
            button.setOnAction(actionEvent -> deleteKey());

            hBox.getChildren().addAll(keyValue, creation, button);
            keyVBox.getChildren().add(hBox);

        }

        scrollPane.setContent(keyVBox);
    }

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

    private void submit(){
        if (secretPass.getText().equals(user.getSecretCode()) && !secretPass.getText().isEmpty()){
            secretCodePane.setDisable(true);
            secretCodePane.setVisible(false);
        } else if (secretPass.getText().isEmpty()) {
            errorInfo.setText("Provide the secret code");
        } else {
            errorInfo.setText("Incorrect code!");
        }
    }

    private void deleteKey(){
        ModifyFiles modifyFiles = new ModifyFiles("src/main/resources/com/example/keys.csv");
        List<CSVRecord> records = modifyFiles.readRecords();
        modifyFiles.modifyRecords(records, this);

        keys();
    }

    private void back(){
        Node nodeRight = paneManager.getPreviousNodes();
        Node nodeLeft = paneManager.getPreviousNodes();
        Node nodeCenter = paneManager.getPreviousNodes();
        Node nodeTop = paneManager.getPreviousNodes();
        borderPane.setTop(nodeTop);
        borderPane.setCenter(nodeCenter);
        borderPane.setLeft(nodeLeft);
        borderPane.setRight(nodeRight);
    }

    @Override
    public void modify(List<CSVRecord> records) {
        for (int i = 0; i < records.size(); i++) {
            CSVRecord record = records.get(i);
            if (record.get(0).equals(String.valueOf(user.getUserId()))) {
                records.remove(record);
            }
        }
    }
}
