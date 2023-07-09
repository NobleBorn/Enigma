package com.example.enigma.Model.Encryption.Shift;

/**
 * The ShiftParent class is an abstract class that defines the structure for a shift cipher algorithm.
 * It provides methods for switching up the cipher key and performing the shift cipher operation.
 */
public abstract class ShiftParent {

    /**
     * Switches up the cipher key to the next available key in the sequence.
     *
     * @param key The current cipher key.
     */
    protected abstract void switchUp(String key);

    /**
     * Performs the shift cipher operation on the given text using the current cipher key.
     *
     * @return The ciphered text.
     */
    protected abstract String shiftCipher();
}

