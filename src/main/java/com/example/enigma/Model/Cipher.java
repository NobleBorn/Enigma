package com.example.enigma.Model;

public class Cipher {

    private final ShiftCipher shiftCipher;
    private final SubstituteCipher substituteCipher;
    private final AsciiCipher asciiCipher;


    public Cipher(String cipherText, String cipherKey) {

        shiftCipher = new ShiftCipher(cipherText, cipherKey);
        String textLevel1 = cipherLevel1();

        substituteCipher = new SubstituteCipher(textLevel1);
        String textLevel2 = cipherLevel2();

        asciiCipher = new AsciiCipher(textLevel2);
    }

    private String cipherLevel1(){
        return shiftCipher.shift();
    }

    private String cipherLevel2(){
        return substituteCipher.substitute();
    }

    public String getCodedText(){
        return asciiCipher.ascii();
    }
}
