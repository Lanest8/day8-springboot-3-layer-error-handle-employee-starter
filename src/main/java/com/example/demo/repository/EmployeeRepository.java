package com.example.demo.repository;


import com.example.demo.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        Stream<Employee> stream = employees.stream();
        if (gender != null) {
            stream = stream.filter(employee -> employee.getGender().compareToIgnoreCase(gender) == 0);
        }
        if (page != null && size != null) {
            stream = stream.skip((long) (page - 1) * size).limit(size);
        }
        return stream.toList();
    }

    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employee.setActiveStatus(true);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee found = getEmployeeById(id);
        found.setName(updatedEmployee.getName());
        found.setAge(updatedEmployee.getAge());
        found.setGender(updatedEmployee.getGender());
        found.setSalary(updatedEmployee.getSalary());
        found.setActiveStatus(updatedEmployee.isActiveStatus());
        return found;
    }

    public void deleteEmployee(int id) {
        employees.stream()
                .filter(e -> e.getId() == id)
                .forEach(e -> e.setActiveStatus(false));
    }
}
