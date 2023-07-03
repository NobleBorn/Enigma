package com.example.enigma;

import com.example.enigma.Model.FileManager;
import com.example.enigma.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KeysPageController {

    @FXML ScrollPane scrollPane;
    @FXML VBox keyVBox;
    @FXML Pane secretCodePane;
    @FXML TextField secretPass;
    @FXML Button submitButton;

    private final PaneManager paneManager;
    private final User user;
    private final FileManager csv;

    public KeysPageController(BorderPane borderPane){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KeysPage.fxml")));
        loader.setController(this);
        paneManager = PaneManager.getInstance();
        user = User.getInstance();
        csv = new FileManager("src/main/resources/com/example/keys.csv");

        try {
            Parent node = loader.load();
            borderPane.setCenter(node);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        submitButton.setOnAction(actionEvent -> submit());
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
        if (secretPass.getText().equals(user.getSecretCode())){
            secretCodePane.setDisable(true);
            secretCodePane.setVisible(false);
        }
    }

    private void deleteKey(){
        try {
            CSVParser csvParser = csv.readFromFile();

            List<CSVRecord> records = csvParser.getRecords();

            modifyRecord(records, String.valueOf(user.getUserId()));

            csvParser.close();

            File existingFile = new File("src/main/resources/com/example/keys.csv");
            boolean deleted = existingFile.delete();

            CSVPrinter csvPrinter = csv.writeToFile();

            if (deleted){
                for (CSVRecord record : records) {
                    csvPrinter.printRecord(record);
                }
            } else {
                System.out.println("Not deleted!");
            }

            csvPrinter.flush();
            csvPrinter.close();
            csvParser.close();

        } catch (IOException e) {
            System.out.println("Error occurred while writing to the CSV file: " + e.getMessage());
        }

        keys();
    }

    private void modifyRecord(List<CSVRecord> records, String id) throws IOException {
        for (int i = 0; i < records.size(); i++) {
            CSVRecord record = records.get(i);
            if (record.get(0).equals(id)) {
                records.remove(record);
            }
        }
    }
}
