package com.dustinredmond.csv;

/*
 *  Copyright 2020 Dustin K. Redmond
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;

public class ObjectToCSVMapper {

    private static ObjectToCSVMapper instance;

    /**
     * Get the instance of ObjectToCSVMapper
     * @return the ObjectToCSVMapper
     */
    public static ObjectToCSVMapper getInstance() {
        if (instance == null) {
            instance = new ObjectToCSVMapper();
        }
        return instance;
    }

    private ObjectToCSVMapper() { super(); }

    /**
     * Converts a single Object to a delimited format
     * @param object An object to convert to a delimited String
     * @param delimiter Delimiter to be used
     * @param addHeader If true, the returned String will include a header
     * @param <T> The parameterized type
     * @return String of delimited text
     * @throws RuntimeException if any error occurs during reflective processes
     */
    public <T> String mapToCsv(T object, String delimiter, boolean addHeader) throws RuntimeException {
        if (object == null || delimiter == null || delimiter.trim().isEmpty()) {
            throw new UnsupportedOperationException("Object and delimiter must both be not null.");
        }

        StringJoiner headerJoiner = new StringJoiner(delimiter);
        StringJoiner dataJoiner = new StringJoiner(delimiter);
        for (Field field : object.getClass().getDeclaredFields()) {
            if (addHeader)
                headerJoiner.add(field.getName());
            try {
                field.setAccessible(true);
                dataJoiner.add(field.get(object).toString());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } finally {
                field.setAccessible(false);
            }
        }
        if (addHeader) {
            return headerJoiner.toString() + NEW_LINE + dataJoiner.toString();
        } else {
            return dataJoiner.toString();
        }
    }

    /**
     * Converts Objects to a delimited format
     * @param objects Objects to convert to a delimited String
     * @param delimiter Delimiter to be used
     * @param addHeader If true, the returned String will include a header
     * @param <T> The parameterized type
     * @return A delimited String representing the objects
     * @throws RuntimeException if any error occurs during reflective processes
     */
    public <T> String mapToCsv(List<T> objects, String delimiter, boolean addHeader) throws RuntimeException {
        if (objects == null || objects.size() == 0) {
            throw new UnsupportedOperationException("Supplied objects must be non null and " +
                    "contain at least one object.");
        }
        if (delimiter == null || delimiter.trim().isEmpty()) {
            throw new UnsupportedOperationException("Delimiter cannot be null or empty.");
        }

        StringBuilder reportBuilder = new StringBuilder();
        if (addHeader) {
            StringJoiner headerJoiner = new StringJoiner(delimiter);
            for (Field field : objects.get(0).getClass().getDeclaredFields()) {
                headerJoiner.add(field.getName());
            }
            reportBuilder.append(headerJoiner.toString());
            reportBuilder.append(NEW_LINE);
        }

        for (Object o : objects) {
            reportBuilder.append(mapToCsv(o, delimiter, false));
            reportBuilder.append(NEW_LINE);
        }

        return reportBuilder.toString();
    }

    private static final String NEW_LINE = "\n";
}
