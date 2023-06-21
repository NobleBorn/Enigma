package com.example.enigma;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class PaneManager {

    private final static PaneManager instance = new PaneManager();
    private final Stack<Node> stack = new Stack<>();

    private PaneManager(){}

    public static PaneManager getInstance(){
        return instance;
    }

    public void next(Node currentPane){
        stack.push(currentPane);
    }

    public Node previous(){
        return stack.pop();
    }

    public Node firstElement(){
        Node element = stack.pop();

        while (!stack.isEmpty()) {
            element = stack.pop();
        }

        return element;
    }

    public Parent loader(String file) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
    }

}
