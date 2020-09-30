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

    public static List<List<String>> getData(Path path, String delimiter) throws IOException {
        List<List<String>> dataList = new ArrayList<>();
        boolean isFirstPass = true;
        for (String line : Files.readAllLines(path)) {
            if (isFirstPass) {
                isFirstPass = false;
                continue;
            }
            dataList.add(Arrays.asList(line.split(delimiter)));
        }
        return dataList;
    }
}
