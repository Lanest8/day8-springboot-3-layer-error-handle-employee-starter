package com.example.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidActiveEmployeeException;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.repository.IEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final IEmployeeRepository employeeRepository;

    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees(String gender, Integer page, Integer size) {
        if (gender == null) {
            if (page == null || size == null) {
                return employeeRepository.findAll();
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeRepository.findAll(pageable).toList();
            }
        } else {
            if (page == null || size == null) {
                return employeeRepository.findEmployeesByGender(gender);
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeRepository.findEmployeesByGender(gender, pageable);
            }
        }
    }

    public Employee getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employee.get();
    }

    public Employee createEmployee(Employee employee) {
        if (employee.getAge() == null) {
            throw new InvalidAgeEmployeeException("employee age is null");
        }
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidAgeEmployeeException("employee age is invalid");
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000) {
            throw new InvalidAgeEmployeeException("employee age is invalid");
        }
        employee.setActiveStatus(true);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee employee = getEmployeeById(id);
        if (!employee.isActiveStatus()) {
            throw new InvalidActiveEmployeeException("Employee is not active");
        }
        updatedEmployee.setId(id);
        if (!Objects.isNull(updatedEmployee.getName())) {
            employee.setName(updatedEmployee.getName());
        }
        if (!Objects.isNull(updatedEmployee.getAge())) {
            employee.setAge(updatedEmployee.getAge());
        }
        if (!Objects.isNull(updatedEmployee.getGender())) {
            employee.setGender(updatedEmployee.getGender());
        }
        if (!Objects.isNull(updatedEmployee.getSalary())) {
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        Employee employee = getEmployeeById(id);
        employee.setActiveStatus(false);
        employeeRepository.save(employee);
    }
}
