package com.example.enigma.Model;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface IChangable {

    void modify(List<CSVRecord> records);
}
