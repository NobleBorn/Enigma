package com.example.enigma.Controller;

/**
 * The IEncodable interface defines the contract for objects that can perform ciphering operations.
 * Classes implementing this interface are responsible for implementing the ciphering logic.
 * @author Mojtaba Alizade
 */
public interface IEncodable {

    /**
     * Performs the ciphering operation.
     * Implementing classes should provide the logic to transform the input data according to the specific algorithm.
     */
    void ciphering();
}

