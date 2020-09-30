package com.dustinredmond.csv.test;

import com.dustinredmond.csv.CSVParts;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class TestCSVParts {

    public static void main(String[] args) {

        // Test CSVParts.getHeader()
        try {
            List<String> header = CSVParts.getHeader(Paths.get("report.csv"), ",");
            for (int i = 0; i < header.size(); i++) {
                System.out.println("header.get(" + i + ") = " + header.get(i));
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Test CSVParts.getData()
        try {
            List<List<String>> data = CSVParts.getData(Paths.get("report.csv"), ",");
            for (int i = 0; i < data.size(); i++) {
                System.out.printf("Row: %s\n", i);
                List<String> row = data.get(i);
                for (int j = 0; j < row.size(); j++) {
                    System.out.println("row.get("+ j +") = " + row.get(j));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
