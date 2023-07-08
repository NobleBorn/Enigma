package com.example.enigma.Model.Interfaces;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface IChangable {

    void modify(List<CSVRecord> records);
}
