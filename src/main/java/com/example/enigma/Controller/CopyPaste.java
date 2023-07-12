package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Timer;

/**
 * The CopyPaste class is responsible for managing the copy and paste functionality in the application.
 * It allows copying and pasting text and provides a timer for tracking the duration of copy or paste operations.
 * @author Mojtaba Alizade
 */
public class CopyPaste {

    private final static CopyPaste instance = new CopyPaste();
    private String text;

    private CopyPaste(){}

    /**
     * Returns the singleton instance of the CopyPaste class.
     * @return The CopyPaste instance.
     */
    protected static CopyPaste getInstance(){
        return instance;
    }

    /**
     * Copies the specified text.
     * @param copied The text to be copied.
     */
    protected void copyText(String copied){
        text = "";
        text += copied;
    }

    /**
     * Retrieves the copied text.
     * @return The copied text.
     */
    protected String pasteText(){
        return text;
    }

    /**
     * Starts a timer for tracking the duration of a copy or paste operation.
     * @param timer The ITimer instance for handling timer events.
     */
    protected void countTime(ITimer timer){
        Timer takeTimer = new Timer();
        takeTimer.timer(timer);
    }
}

