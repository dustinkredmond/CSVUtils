package com.dustinredmond.csv.test;

import com.dustinredmond.csv.CSVToObjectMapper;
import com.dustinredmond.csv.test.model.Employee;

import java.util.List;

public class TestCSVToObjectMapper {

    public static void main(String[] args) {

        CSVToObjectMapper mapper = new CSVToObjectMapper();
        List<Employee> employeeList = mapper.map(CSV, ",", Employee.class);

        assert "john".equals(employeeList.get(0).getFirstName());
        assert "jane".equals(employeeList.get(1).getFirstName());
    }

    private static final String CSV = "id,age,salary,firstName,lastName\n1,25,52000,john,smith\n2,27,63000,jane,doe";

}
