package com.example.enigma.Model;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip clip;
    private static SoundManager instance = null;

    private SoundManager() {
        try {
            File soundFile = new File("src/main/resources/com/example/enigma/button.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SoundManager getInstance()
    {
        if (instance == null)
            instance = new SoundManager();

        return instance;
    }

    public Clip getClip() {
        return clip;
    }
}
