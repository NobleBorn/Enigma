package com.example.enigma;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

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

}