package com.example.enigma.Model.Encryption.Ascii;

/**
 * The AsciiParent class is an abstract class that provides a template for performing ASCII cipher operations.
 * Subclasses extending this class should implement the asciiCipher method to define the specific logic for
 * performing the ASCII cipher.
 * @author Mojtaba Alizade
 */
public abstract class AsciiParent {

    /**
     * Performs the ASCII cipher operation.
     * Subclasses should implement this method to define the specific logic for the ASCII cipher.
     *
     * @return The result of the ASCII cipher operation.
     */
    protected abstract String asciiCipher();
}

