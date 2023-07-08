package com.example.enigma.Model;

import com.example.enigma.Model.Interfaces.ITimer;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class Timer {

    public Timer(){}

    public void timer(ITimer timers){
        int timeDuration = 1000;

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
            Platform.runLater(timers::afterTimer);
        });

        timers.duringTimer();
        new Thread(timerTask).start();
    }
}
