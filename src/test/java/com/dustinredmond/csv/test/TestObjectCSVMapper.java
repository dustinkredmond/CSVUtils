package com.dustinredmond.csv.test;

import com.dustinredmond.csv.ObjectCSVMapper;

import java.util.ArrayList;
import java.util.List;

public class TestObjectCSVMapper {

    public static void main(String[] args) {
        ObjectCSVMapper mapper = ObjectCSVMapper.getInstance();
        Student s = new Student("John", "Smith");
        System.out.println("Mapping Student('John', 'Smith') to String");
        System.out.println(mapper.mapToCsv(s, ",", true));
        System.out.println("\n");
        System.out.println("Mapping without header...");
        System.out.println(mapper.mapToCsv(s, ",", false));
        System.out.println("\n");

        System.out.println("Mapping multiple objects to CSV:");
        Student s2 = new Student("Jane", "Doe");
        List<Student> students = new ArrayList<>();
        students.add(s);
        students.add(s2);
        System.out.println(mapper.mapToCsv(students, ",", true));
    }

    private static class Student {
        private String firstName;
        private String lastName;

        public Student(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
