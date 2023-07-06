package com.example.enigma;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.util.Stack;

public class PaneManager {

    private final static PaneManager instance = new PaneManager();
    private final Stack<Node> pageNodes = new Stack<>();
    private BorderPane borderPane;
    private Node[] homePage = new Node[4];
    private Node[] startPage = new Node[4];

    private PaneManager(){}

    public static PaneManager getInstance(){
        return instance;
    }

    public void saveCurrentNodes(Node currentPane){
        pageNodes.push(currentPane);
    }

    public Node getPreviousNodes(){
        return pageNodes.pop();
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setStartPage(Node[] startPage) {
        this.startPage = startPage;
    }

    public Node[] getStartPage() {
        return startPage;
    }

    public void setHomePage(Node[] homePage) {
        this.homePage = homePage;
    }

    public Node[] getHomePage() {
        return homePage;
    }
}
