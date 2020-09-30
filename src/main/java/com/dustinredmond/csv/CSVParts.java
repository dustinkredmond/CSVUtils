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
import java.util.StringJoiner;

/**
 * Class for dealing with individual parts of a delimited file.
 */
public class CSVParts {

    /**
     * Returns the header of a given delimited file
     * as a List of Strings
     * @param path Path to the delimited file
     * @param delimiter The delimiter used in the file
     * @return Header values as a List of Strings
     * @throws IOException If reading the file fails
     */
    public static List<String> getHeader(Path path, String delimiter) throws IOException {
        String firstLine = Files.readAllLines(path).get(0);
        return new ArrayList<>(Arrays.asList(firstLine.split(delimiter)));
    }

    /**
     * Returns the header of a given delimited String
     * as a List of Strings
     * @param csvContent The contents of a delimited file
     * @param delimiter The delimiter used in the `csvContent`
     * @return Header values as a List of Strings
     */
    public static List<String> getHeader(String csvContent, String delimiter) {
        return new ArrayList<>(Arrays.asList(csvContent.split("\n")[0].split(delimiter)));
    }

    /**
     * Returns the data of the given delimited file without the
     * header. A list is returned containing Lists of Strings.
     * Each list contains the values of the given row.
     * @param path Path to the delimited file
     * @param delimiter Delimiter used in the passed file path.
     * @return {@code List<List<String>>} Each line of the delimited file as a
     * List of Strings
     * @throws IOException If the file cannot be read
     */
    public static List<List<String>> getData(Path path, String delimiter) throws IOException {
        List<List<String>> dataList = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            dataList.add(Arrays.asList(line.split(delimiter)));
        }
        dataList.remove(0); // remove header
        return dataList;
    }

    /**
     * Returns the data of a given delimited String. A List is
     * returned containing Lists of Strings. Each list contains
     * the values of the given row.
     * @param csvContent Delimited content
     * @param delimiter Delimiter used in `csvContent`
     * @return {@code List<List<String>>} Each line of the delimited file as a
     * List of Strings
     */
    public static List<List<String>> getData(String csvContent, String delimiter) {
        List<String> dataRows = new ArrayList<>(Arrays.asList(csvContent.split("\n")));
        dataRows.remove(0); // remove header

        List<List<String>> data = new ArrayList<>();
        for (String row : dataRows) {
            data.add(new ArrayList<>(Arrays.asList(row.split(delimiter))));
        }
        return data;
    }

    /**
     * Returns a String representing a merging of two delimited
     * files. The second header is dropped.
     * @param firstFile The first file to merge
     * @param secondFile The second file to merge
     * @return A String of the "merged" files
     * @throws IOException If either file cannot be read
     */
    public static String getMergedCsv(Path firstFile, Path secondFile) throws IOException {
        StringJoiner sj = new StringJoiner("\n");
        Files.readAllLines(firstFile).forEach(sj::add);
        List<String> secondFileContents = Files.readAllLines(secondFile);
        secondFileContents.remove(0); // remove second header
        secondFileContents.forEach(sj::add);
        return sj.toString();
    }
}
