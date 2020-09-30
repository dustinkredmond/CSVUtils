package com.dustinredmond.csv;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

public class ObjectDelimitedReport {

    private String delimiter;
    private Path outputPath;
    private List<?> objects;
    private List<String> ignoreFields = new ArrayList<>();
    private HashMap<String, String> alternateNames = new HashMap<>();

    public ObjectDelimitedReport withDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    public ObjectDelimitedReport outputTo(Path outputPath) {
        this.outputPath = outputPath;
        return this;
    }

    public ObjectDelimitedReport ignoringFields(List<String> ignoreFields) {
        this.ignoreFields = ignoreFields;
        return this;
    }

    public ObjectDelimitedReport withAlternateNames(HashMap<String,String> alternateNames) {
        this.alternateNames = alternateNames;
        return this;
    }

    public <T> ObjectDelimitedReport fromObjects(List<T> objects) {
        this.objects = objects;
        return this;
    }

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
