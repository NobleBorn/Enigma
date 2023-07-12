package com.example.enigma.Model.Interfaces;

/**
 * The ITimer interface represents a timer functionality with two methods: duringTimer and afterTimer.
 * Classes implementing this interface should provide implementations for these methods to define the behavior
 * during and after the timer duration.
 * @author Mojtaba Alizade
 */
public interface ITimer {

    /**
     * Represents the behavior during the timer duration.
     * Implementing classes should define the actions to be performed during the timer.
     */
    void duringTimer();

    /**
     * Represents the behavior after the timer duration.
     * Implementing classes should define the actions to be performed after the timer has finished.
     */
    void afterTimer();
}
