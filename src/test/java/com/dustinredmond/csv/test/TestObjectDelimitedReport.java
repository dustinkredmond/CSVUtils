package com.dustinredmond.csv.test;

import com.dustinredmond.csv.ObjectDelimitedReport;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TestObjectDelimitedReport {

    public static void main(String[] args) {

        Employee e1 = new Employee(1L, "John", "Smith");
        Employee e2 = new Employee(2L, "Jane", "Doe");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e1);
        employeeList.add(e2);

        new ObjectDelimitedReport().outputTo(Paths.get("report.csv"))
            .withDelimiter(",")
            .ignoringFields(Collections.singletonList("id"))
            .fromObjects(employeeList)
            .saveReport();


        HashMap<String,String> nameMap = new HashMap<>();
        nameMap.put("lastName", "surname");
        nameMap.put("firstName", "first name");

        new ObjectDelimitedReport().outputTo(Paths.get("report2.txt"))
                .withDelimiter("\t")
                .withAlternateNames(nameMap)
                .fromObjects(employeeList)
                .saveReport();
    }

    static class Employee {
        private final long id;
        private final String firstName;
        private final String lastName;

        public Employee(long id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
