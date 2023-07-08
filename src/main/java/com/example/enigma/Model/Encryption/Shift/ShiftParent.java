package com.example.enigma.Model.Encryption.Shift;

public abstract class ShiftParent {
    protected abstract void switchUp(String key);
    protected abstract String shiftCipher();
}
