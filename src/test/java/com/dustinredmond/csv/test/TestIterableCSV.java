package com.dustinredmond.csv.test;

import com.dustinredmond.csv.IterableCSV;

import java.io.IOException;
import java.nio.file.Paths;

public class TestIterableCSV {
    public static void main(String[] args) {
        try {
            IterableCSV csv = new IterableCSV();
            csv.loadFile(Paths.get("report.csv"));
            System.out.printf("First Iteration = %s\n", csv.getNextLine());
            System.out.printf("Second Iteration = %s\n", csv.getNextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
