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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A mapper used to convert basic delimited text into
 * Java Objects.
 */
public class CSVToObjectMapper {

    /**
     * Converts entities in delimited text to Java Objects.
     * @param csv Delimited String containing data that represents Java Objects
     * @param delimiter The delimiter used in `csv`
     * @param theClass The Java Object which the CSV entries will be mapped to.
     * @param <T> The returned parameterized type.
     * @return A list of Objects constructed from the delimited text.
     */
    public <T> List<T> map(String csv, String delimiter, Class<T> theClass) {
        List<String> headerList = CSVParts.getHeader(csv, delimiter);
        List<List<String>> data = CSVParts.getData(csv, delimiter);
        List<T> returnList = new ArrayList<>();

        data.forEach(row -> {
            try {
                T theObject;
                theObject = theClass.newInstance();
                for (Field field : theClass.getDeclaredFields()) {
                    int headerIndex = headerList.indexOf(field.getName());
                    field.setAccessible(true);
                    setAccordingToDataType(field,theObject,row.get(headerIndex));
                    field.setAccessible(false);
                }
                returnList.add(theObject);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return returnList;
    }

    private <T> void setAccordingToDataType(Field field, T o, String data) throws IllegalAccessException {

        // This is about to get very ugly...
        // Necessary, as Java will throw exception if trying to
        // set,for example, an int to a long field
        Class<?> type = field.getType();
        if (type.equals(Integer.class) || type.equals(int.class)) {
            field.setInt(o, Integer.parseInt(data));
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            field.setLong(o, Long.parseLong(data));
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            field.setDouble(o, Double.parseDouble(data));
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            field.setBoolean(o, Boolean.parseBoolean(data));
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            field.setByte(o, Byte.parseByte(data));
        } else if (type.equals(Character.class) || type.equals(char.class)) {
            field.setChar(o, data.charAt(0));
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            field.setFloat(o, Float.parseFloat(data));
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            field.setShort(o, Short.parseShort(data));
        } else {
            try {
                // Now we assume our object is a java.lang.Object
                field.set(o, data);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(String.format("Unable to set (%s) to field of " +
                        "type %s. Try using a primitive data type or simple" +
                        " type (e.g. java.lang.String)", data, type));
            }
        }
    }
}
