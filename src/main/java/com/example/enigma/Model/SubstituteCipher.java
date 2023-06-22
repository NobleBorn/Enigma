package com.example.enigma.Model;

import java.util.HashMap;

public class SubstituteCipher {


    HashMap<Character, Character> code = new HashMap<>();

    public SubstituteCipher(){
        populateCode();

    }

    private void populateCode(){

        for (int i = 65; i <= 77; i++){
            code.put((char) i, (char) (i+13));
        }
    }
}
