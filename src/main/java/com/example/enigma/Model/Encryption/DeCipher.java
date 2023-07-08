package com.example.enigma.Model.Encryption;

import com.example.enigma.Model.Encryption.Ascii.AsciiDeCipher;
import com.example.enigma.Model.Encryption.Shift.ShiftDeCipher;
import com.example.enigma.Model.Encryption.Substitute.SubstituteDeCipher;

public class DeCipher {

    private final ShiftDeCipher shiftDeCipher;
    private final SubstituteDeCipher substituteDeCipher;
    private final AsciiDeCipher asciiDeCipher;

    public DeCipher(String deCipherText, String deCipherKey){
        asciiDeCipher = new AsciiDeCipher(deCipherText);
        String deCipherLevel1 = deCipherLevel1();

        substituteDeCipher = new SubstituteDeCipher(deCipherLevel1, deCipherKey);
        String deCipherLevel2 = deCipherLevel2();

        shiftDeCipher = new ShiftDeCipher(deCipherLevel2, deCipherKey);
    }

    private String deCipherLevel1(){
        return asciiDeCipher.asciiCipher();
    }

    private String deCipherLevel2(){
        return substituteDeCipher.deSubstitute();
    }

    public String getDeCodedText(){
        return shiftDeCipher.shiftCipher();
    }
}
