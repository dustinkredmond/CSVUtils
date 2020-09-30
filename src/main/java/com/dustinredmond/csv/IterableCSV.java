package com.dustinredmond.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class IterableCSV {

    private final List<String> csvPayload = new ArrayList<>();
    private int index = 0;

    public void loadFile(Path path) throws IOException {

        csvPayload.addAll(Files.readAllLines(path));
    }

    public String getNextLine() {
        return (index >= csvPayload.size()) ? null : csvPayload.get(index++);
    }
}
