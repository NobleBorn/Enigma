package com.example.enigma.Model.Interfaces;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * The IChangeable interface represents the capability to modify a list of CSV records.
 * Classes implementing this interface should provide an implementation for the modify method.
 */
public interface IChangeable {

    /**
     * Modifies the given list of CSV records.
     *
     * @param records The list of CSV records to be modified.
     */
    void modify(List<CSVRecord> records);
}
