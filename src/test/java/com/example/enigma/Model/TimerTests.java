package com.example.enigma.Model;

import com.example.enigma.Model.Interfaces.ITimer;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class TimerTests {

    @Test
    public void testTimer() throws InterruptedException {
        // Create a mock ITimer object
        Timer timer = new Timer();

        // Create a custom implementation of the ITimer interface
        ITimer customTimer = new ITimer() {
            @Override
            public void duringTimer() {

            }

            @Override
            public void afterTimer() {

            }
        };

        // Call the timer() method passing the customTimer object
        timer.timer(customTimer);

    }
}
