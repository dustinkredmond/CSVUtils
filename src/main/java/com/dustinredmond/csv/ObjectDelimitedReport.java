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
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

/**
 * A class for building delimited reports
 * from Java Objects. Uses "Builder" syntax
 * to make this a painless process.
 */
public class ObjectDelimitedReport {

    private String delimiter;
    private Path outputPath;
    private List<?> objects;
    private List<String> ignoreFields = new ArrayList<>();
    private HashMap<String, String> alternateNames = new HashMap<>();

    /**
     * Specifies the delimiter to be used when creating the report.
     * @param delimiter The delimiter to be used
     * @return the report
     */
    public ObjectDelimitedReport withDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    /**
     * Specifies the save location of the report.
     * @param outputPath The save location of the report
     * @return the report
     */
    public ObjectDelimitedReport outputTo(Path outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    /**
     * Specifies fields to ignore when creating the report
     * @param ignoreFields Fields not to be added to the report
     * @return the report
     */
    public ObjectDelimitedReport ignoringFields(List<String> ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }

    /**
     * Alternative naming to be used.
     * {@code hashMap.put("someJavaColumn", "A pretty name");}
     * can be used to change the name from the Java field name
     * to a user-specified value for the final report.
     * @param alternateNames Alternative names for Java field names
     * @return the report
     */
    public ObjectDelimitedReport withAlternateNames(HashMap<String,String> alternateNames) {
        this.alternateNames = alternateNames;
        return this;
    }

    /**
     * Specifies the Java Objects from which to create the report.
     * @param objects List of Objects to appear on report
     * @param <T> The parameterized type
     * @return the report
     */
    public <T> ObjectDelimitedReport fromObjects(List<T> objects) {
        this.objects = objects;
        return this;
    }

    /**
     * Saves the report to the selected location. This method should
     * be called after setting the Objects, delimiter, and output Path.
     * @throws RuntimeException If a value is not properly set before report
     * generation.
     */
    public void saveReport() throws RuntimeException {
        if (outputPath == null) {
            throw new UnsupportedOperationException("Output path must be set.");
        }
        if (objects == null || objects.size() == 0) {
            throw new UnsupportedOperationException("Report entities must be set.");
        }

        final String reportString = buildReportString();
        try {
            Files.write(this.outputPath, reportString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildReportString() {
        StringBuilder sb = new StringBuilder();

        final String delimiter = this.delimiter != null ? this.delimiter : COMMA;
        StringJoiner sj = new StringJoiner(delimiter);
        for (Field field : objects.get(0).getClass().getDeclaredFields()) {
            final String fieldName = field.getName();
            if (!ignoreFields.contains(fieldName)) {
                sj.add(alternateNames.getOrDefault(fieldName, fieldName));
            }
        }
        sb.append(sj.toString());

        for (Object object : objects) {
            sb.append("\n");
            sj = new StringJoiner(delimiter);
            for (Field field : object.getClass().getDeclaredFields()) {
                if (ignoreFields.contains(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                try {
                    sj.add(field.get(object).toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field ("+field.getName()+") value.", e);
                } finally {
                    field.setAccessible(false);
                }
            }
            sb.append(sj.toString());
        }
        return sb.toString();
    }

    private static final String COMMA = ",";
}
