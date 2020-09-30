package com.dustinredmond.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVParts {

    public static List<String> getHeader(Path path, String delimiter) throws IOException {
        String firstLine = Files.readAllLines(path).get(0);
        return new ArrayList<>(Arrays.asList(firstLine.split(delimiter)));
    }

    public static List<String> getHeader(String csvContent, String delimiter) {
        return new ArrayList<>(Arrays.asList(csvContent.split("\n")[0].split(delimiter)));
    }

    public static List<List<String>> getData(Path path, String delimiter) throws IOException {
        List<List<String>> dataList = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            dataList.add(Arrays.asList(line.split(delimiter)));
        }
        dataList.remove(0); // remove header
        return dataList;
    }

    public static List<List<String>> getData(String csvContent, String delimiter) {
        List<String> dataRows = new ArrayList<>(Arrays.asList(csvContent.split("\n")));
        dataRows.remove(0); // remove header

        List<List<String>> data = new ArrayList<>();
        for (String row : dataRows) {
            data.add(new ArrayList<>(Arrays.asList(row.split(delimiter))));
        }
        return data;
    }
}
