package com.assessme.service;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
public class CSVImport {
    public static CSVReader importFromUrl(String url) throws IOException{
            URL csvUrl = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(csvUrl.openStream()));
        return new CSVReader(in);
    }
}
