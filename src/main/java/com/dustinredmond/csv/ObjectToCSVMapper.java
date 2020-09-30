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
    public static ObjectToCSVMapper getInstance() {
        if (instance == null) {
            instance = new ObjectToCSVMapper();
        }
        return instance;
    }

    private ObjectToCSVMapper() { super(); }

    public <T> String mapToCsv(T o, String delimiter, boolean addHeader) {
        if (o == null || delimiter == null || delimiter.trim().isEmpty()) {
            throw new UnsupportedOperationException("Object and delimiter must both be not null.");
        }

        StringJoiner headerJoiner = new StringJoiner(delimiter);
        StringJoiner dataJoiner = new StringJoiner(delimiter);
        for (Field field : o.getClass().getDeclaredFields()) {
            if (addHeader)
                headerJoiner.add(field.getName());
            try {
                field.setAccessible(true);
                dataJoiner.add(field.get(o).toString());
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
