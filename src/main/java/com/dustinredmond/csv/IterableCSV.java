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
import java.util.List;

/**
 * Represents a easily-iterable delimited file
 */
public class IterableCSV {

    /**
     * Takes in a delimited file and loads it into memory.
     * Calls to {@code new IterableCSV(someFilePath).getNextLine()}
     * will return a representation of the next line of data.
     * @param path A path to a delimited file
     * @throws RuntimeException If the path is unable to be read
     */
    public IterableCSV(Path path) throws RuntimeException {
        try {
            csvPayload.addAll(Files.readAllLines(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the next line in the delimited file, or
     * null if the end has been reached.
     * @return The next line of the delimited file
     */
    public String getNextLine() {
        return (index >= csvPayload.size()) ? null : csvPayload.get(index++);
    }

    private final List<String> csvPayload = new ArrayList<>();
    private int index = 0;
}
