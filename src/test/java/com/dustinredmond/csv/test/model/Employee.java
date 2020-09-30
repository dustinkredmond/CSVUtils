package com.dustinredmond.csv.test.model;

public class Employee {
    private long id;
    private int age;
    private double salary; // DON'T USE DOUBLE FOR CURRENCY
    private String firstName;
    private String lastName;

    // Must have this for CSVToObjectMapper to work
    public Employee() { super(); }

    public Employee(long id, int age, double salary, String firstName, String lastName) {
        this.id = id;
        this.age = age;
        this.salary = salary;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s,%s,%s,%s]", this.id, this.age, this.salary,
                this.firstName, this.lastName);
    }
}
