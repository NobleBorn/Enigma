package com.example.enigma.Model;

import javafx.scene.Node;

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
