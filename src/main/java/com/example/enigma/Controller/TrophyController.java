package com.example.enigma.Controller;

import com.example.enigma.Model.Trophy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import java.io.IOException;

/**
 * The controller class for the TrophyPage.
 * It manages the functionality related to trophies and handles user interactions.
 * @author Mojtaba Alizade
 */
public class TrophyController {

    @FXML Pane cipherTrophy;
    @FXML Pane keyTrophy;
    @FXML Pane decipherTrophy;
    @FXML ImageView cipherImage;
    @FXML ImageView keyImage;
    @FXML ImageView decipherImage;
    @FXML ImageView goingBack;

    private final PaneManager paneManager;
    private final BorderPane borderPane;
    private final Rectangle2D bounds;

    /**
     * Initializes a new instance of the TrophyController class.
     * Sets up the necessary dependencies and loads the associated FXML file.
     */
    public TrophyController(){
        Screen screen = Screen.getPrimary();
        bounds = screen.getVisualBounds();
        paneManager = PaneManager.getInstance();
        borderPane = paneManager.getBorderPane();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/enigma/TrophyPage.fxml"));
        fxmlLoader.setController(this);

        try {
            Node root = fxmlLoader.load();
            borderPane.setCenter(root);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initializeTrophies();
        goingBack.setOnMouseClicked(mouseEvent -> back());
        lookUpTrophies();
    }

    private void initializeTrophies(){
        cipherTrophy.setLayoutX(bounds.getWidth() * 0.208);
        keyTrophy.setLayoutX(bounds.getWidth() * 0.448);
        decipherTrophy.setLayoutX(bounds.getWidth() * 0.677);
    }

    /**
     * Handles the action of going back to the main page.
     */
    private void back(){
        Node[] home = paneManager.getHomePage();
        borderPane.setTop(home[0]);
        borderPane.setCenter(home[1]);
        borderPane.setLeft(home[2]);
        borderPane.setRight(home[3]);
    }

    /**
     * Looks up the trophies earned by the user and updates the UI accordingly.
     * Enables and disables trophy panes based on the user's achievements.
     */
    private void lookUpTrophies(){
        Trophy trophy = new Trophy("src/main/resources/com/example/enigma/trophy.csv");

        if (trophy.getCipherTrophy() >= 20){
            cipherTrophy.setDisable(false);
            cipherImage.setEffect(null);
        }

        if (trophy.getKeyTrophy() >= 20){
            keyTrophy.setDisable(false);
            keyImage.setEffect(null);
        }

        if (trophy.getDecipherTrophy() >= 20){
            decipherTrophy.setDisable(false);
            decipherImage.setEffect(null);
        }
    }
}
