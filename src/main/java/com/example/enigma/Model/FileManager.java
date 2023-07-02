package com.example.enigma.Model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class FileManager {

    private final String filePath;

    public FileManager(String filePath){
        this.filePath = filePath;
    }

    public CSVParser readFromFile() throws IOException {
        Reader reader = new FileReader(filePath);
        return new CSVParser(reader, CSVFormat.DEFAULT);
    }

    public CSVPrinter writeToFile() throws IOException {
        FileWriter writer = new FileWriter(filePath, true);
        return new CSVPrinter(writer, CSVFormat.DEFAULT);
    }
}
