package com.example.enigma.Controller;

import com.example.enigma.Model.Interfaces.ITimer;
import com.example.enigma.Model.Timer;

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

    public void countTime(ITimer timer){
        Timer takeTimer = new Timer();
        takeTimer.timer(timer);
    }
}
