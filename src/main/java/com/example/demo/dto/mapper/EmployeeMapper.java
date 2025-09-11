package com.example.demo.dto.mapper;

import com.example.demo.dto.EmployeeRequest;
import com.example.demo.dto.EmployeeResponse;
import com.example.demo.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeMapper {
    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }

    public List<EmployeeResponse> toResponse(List<Employee> employees) {
        return employees.stream().map(this::toResponse).toList();
    }

    public Employee toEntity(EmployeeResponse employeeResponse) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeResponse, employee);
        return employee;
    }

    public Employee toEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public EmployeeRequest toRequest(Employee employee) {
        EmployeeRequest employeeRequest = new EmployeeRequest();
        BeanUtils.copyProperties(employee, employeeRequest);
        return employeeRequest;
    }
}
