package com.example.enigma.Model;

import java.io.FileWriter;
import java.io.IOException;

public class User {

    private int id;
    private String userName;
    private String passWord;

    public User(int id, String userName, String passWord){
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
    }


}
