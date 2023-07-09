package com.example.enigma.Model;

import com.example.enigma.Model.Interfaces.ITimer;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * The Timer class provides a simple way to execute code with a specified time delay.
 * It allows you to execute a callback method during the timer duration and after the timer has completed.
 */
public class Timer {

    /**
     * Starts a timer with the specified duration and executes the provided ITimer object's methods.
     *
     * @param timers The ITimer object containing the methods to be executed during and after the timer duration.
     */
    public void timer(ITimer timers){
        int timeDuration = 1000; // Time duration in milliseconds

        Task<Void> timerTask = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(timeDuration); // Sleep for the specified duration
                } catch (InterruptedException e) {
                    System.out.println("Timer interrupted!");
                }
                return null;
            }
        };

        timerTask.setOnSucceeded(event -> {
            Platform.runLater(timers::afterTimer); // Execute the afterTimer method on the JavaFX Application Thread
        });

        timers.duringTimer(); // Execute the duringTimer method

        // Start a new thread to execute the timer task
        new Thread(timerTask).start();
    }
}

