package com.example.enigma.Controller;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import java.util.Stack;

/**
 * The PaneManager class is responsible for managing the navigation between different panes in the application.
 * It keeps track of the previous nodes (panes) and provides methods to save and retrieve them.
 * @author Mojtaba Alizade
 */
public class PaneManager {

    private final static PaneManager instance = new PaneManager();
    private final Stack<Node> pageNodes = new Stack<>();
    private BorderPane borderPane;
    private Node[] homePage = new Node[4];
    private Node[] startPage = new Node[4];
    private Stack<String> previous = new Stack<>();

    private PaneManager(){}

    /**
     * Returns the singleton instance of the PaneManager class.
     * @return The PaneManager instance.
     */
    protected static PaneManager getInstance(){
        return instance;
    }

    /**
     * Saves the current pane node to the stack of previous nodes.
     * @param currentPane The current pane node to be saved.
     */
    protected void saveCurrentNodes(Node currentPane){
        pageNodes.push(currentPane);
    }

    /**
     * Retrieves the previous pane node from the stack of previous nodes.
     * @return The previous pane node.
     */
    protected Node getPreviousNodes(){
        return pageNodes.pop();
    }


    /**
     * Sets the BorderPane instance used for managing panes.
     * @param borderPane The BorderPane instance.
     */
    protected void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    /**
     * Returns the BorderPane instance used for managing panes.
     * @return The BorderPane instance.
     */
    protected BorderPane getBorderPane() {
        return borderPane;
    }

    /**
     * Sets the start page nodes, which represent the initial state of the application.
     * @param startPage The array of start page nodes.
     */
    protected void setStartPage(Node[] startPage) {
        this.startPage = startPage;
    }

    /**
     * Returns the start page nodes, which represent the initial state of the application.
     * @return The array of start page nodes.
     */
    protected Node[] getStartPage() {
        return startPage;
    }

    /**
     * Sets the home page nodes, which represent the main page of the application.
     * @param homePage The array of home page nodes.
     */
    protected void setHomePage(Node[] homePage) {
        this.homePage = homePage;
    }

    /**
     * Returns the home page nodes, which represent the main page of the application.
     * @return The array of home page nodes.
     */
    protected Node[] getHomePage() {
        return homePage;
    }

    /**
     * Push the previous page in the stack
     * @param previousPage The previous page
     */
    protected void setPrevious(String previousPage) {
        if (this.previous.isEmpty()) this.previous.push("");

        if (!this.previous.peek().equals(previousPage)) {
            this.previous.push(previousPage);
        }
    }

    /**
     * Gets the previous page from the stack
     * @return The previous page
     */
    protected String getPrevious() {
        if (this.previous.isEmpty()) return "";
        else return this.previous.peek();
    }

    /**
     * Removes the previous page from the stack
     */
    protected void removePrevious() {
        if (!this.previous.isEmpty())
            this.previous.pop();
    }

    /**
     * Empty the stack
     */
    protected void emptyStack(){
        this.previous = new Stack<>();
    }

}

