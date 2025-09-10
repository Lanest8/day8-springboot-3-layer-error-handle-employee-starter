package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(@RequestParam(required = false) String gender, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return employeeRepository.getEmployees(gender, page, size);
    }

    public Employee getEmployeeById(@PathVariable int id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employee;
    }

    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.getEmployeeById(id);

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    public void deleteEmployee(@PathVariable int id) {
        employeeRepository.deleteEmployee(id);
    }

    public void deleteAllEmployees() {
        employeeRepository.deleteEmployeeAll();
    }
}
