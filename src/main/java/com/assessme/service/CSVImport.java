package com.assessme.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVImport {
    public static CSVReader importFromUrl(String url) throws IOException{
            URL csvUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(csvUrl.openStream()));
        return new CSVReaderBuilder(in).withSkipLines(1).build();
    }

    public static CSVReader importFromPath(Path filePath) throws IOException{
        Reader reader = Files.newBufferedReader(filePath);
        return new CSVReaderBuilder(reader).withSkipLines(1).build();
    }
}
