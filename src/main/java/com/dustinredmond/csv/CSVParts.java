package com.dustinredmond.csv;

/*
 *  Copyright 2020 Dustin K. Redmond
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
