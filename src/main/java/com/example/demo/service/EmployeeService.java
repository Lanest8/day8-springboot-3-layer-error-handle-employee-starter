package com.example.demo.service;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.dto.mapper.EmployeeMapper;
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
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getEmployees(String gender, Integer page, Integer size) {
        if (gender == null) {
            if (page == null || size == null) {
                return employeeMapper.toResponse(employeeRepository.findAll());
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeMapper.toResponse(employeeRepository.findAll(pageable).toList());
            }
        } else {
            if (page == null || size == null) {
                return employeeMapper.toResponse(employeeRepository.findEmployeesByGender(gender));
            } else {
                Pageable pageable = PageRequest.of(page - 1, size);
                return employeeMapper.toResponse(employeeRepository.findEmployeesByGender(gender, pageable));
            }
        }
    }

    public EmployeeResponse getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        return employeeMapper.toResponse(employee.get());
    }

    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        if (employee.getAge() == null) {
            throw new InvalidAgeEmployeeException("employee age is null");
        }
        if (employee.getAge() < 18 || employee.getAge() > 65) {
            throw new InvalidAgeEmployeeException("employee age is invalid");
        }
        if (employee.getAge() > 30 && employee.getSalary() < 20000) {
            throw new InvalidAgeEmployeeException("employee age is invalid");
        }
        employee.setActive(true);
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public EmployeeResponse updateEmployee(int id, EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(getEmployeeById(id));
        if (!employee.isActive()) {
            throw new InvalidActiveEmployeeException("Employee is not active");
        }
        Employee updatedEmployee = employeeMapper.toEntity(employeeRequest);
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
        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    public void deleteEmployee(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found with id: " + id);
        }
        employee.get().setActive(false);
        employeeRepository.save(employee.get());
    }
}
