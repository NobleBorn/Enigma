package com.example.enigma;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CopyPaste {

    private final static CopyPaste instance = new CopyPaste();
    private String text;

    private CopyPaste(){}

    public static CopyPaste getInstance(){
        return instance;
    }

    public void copyText(String copied){
        text = "";
        text += copied;
    }

    public String pasteText(){
        return text;
    }

    public void timer(Label infoLabel, String info, Button button){
        int timeDuration = 2000;

        Task<Void> timerTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(timeDuration);
                } catch (InterruptedException e) {
                    System.out.println("Timer interrupted!");
                }
                return null;
            }
        };

        timerTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                infoLabel.setText("");
                button.setDisable(false);
            });
        });

        infoLabel.setText(info);
        button.setDisable(true);
        new Thread(timerTask).start();
    }
}
