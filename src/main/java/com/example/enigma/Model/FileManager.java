package com.example.enigma.Model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

/**
 * The FileManager class provides methods to read from and write to a CSV file.
 * It uses Apache Commons CSV library for parsing and printing CSV data.
 */
public class FileManager {

    private final String filePath;

    /**
     * Constructs a FileManager object with the specified file path.
     *
     * @param filePath The path to the CSV file.
     */
    public FileManager(String filePath){
        this.filePath = filePath;
    }

    /**
     * Reads the contents of the CSV file and returns a CSVParser object for further processing.
     *
     * @return A CSVParser object representing the parsed CSV data.
     * @throws IOException If an I/O error occurs while reading the CSV file.
     */
    public CSVParser readFromFile() throws IOException {
        Reader reader = new FileReader(filePath);
        return new CSVParser(reader, CSVFormat.DEFAULT);
    }

    /**
     * Creates a CSVPrinter object for writing to the CSV file.
     * The file is opened in append mode, allowing new records to be added to the existing content.
     *
     * @return A CSVPrinter object for printing CSV data.
     * @throws IOException If an I/O error occurs while writing to the CSV file.
     */
    public CSVPrinter writeToFile() throws IOException {
        FileWriter writer = new FileWriter(filePath, true);
        return new CSVPrinter(writer, CSVFormat.DEFAULT);
    }
}

