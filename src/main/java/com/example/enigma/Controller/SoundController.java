package com.example.enigma.Controller;

import com.example.enigma.Model.SoundManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class SoundController {

    private final SoundManager soundManager = SoundManager.getInstance();

    public void sound(Button btn){
        btn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (soundManager.getClip() != null) {
                    soundManager.getClip().setFramePosition(0); // Reset the clip to the beginning
                    soundManager.getClip().start(); // Play the sound
                }
            }
        });
    }
}
