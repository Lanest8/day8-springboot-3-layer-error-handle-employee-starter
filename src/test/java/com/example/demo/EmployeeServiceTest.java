package com.example.demo;

import com.example.demo.dto.mapper.EmployeeMapper;
import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidActiveEmployeeException;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.repository.IEmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    @Mock
    private IEmployeeRepository employeeRepository;

    @Test
    void should_throw_exception_when_create_a_employee() {
        Employee employee = new Employee(1, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee employeeResult = employeeMapper.toEntity(employeeService.createEmployee(employeeMapper.toRequest(employee)));
        assertEquals(employee.getId(), employeeResult.getId());
        assertEquals(employee.getName(), employeeResult.getName());
        assertEquals(employee.getAge(), employeeResult.getAge());
        assertEquals(employee.getGender(), employeeResult.getGender());
    }

    @Test
    void should_throw_exception_when_create_a_employee_of_greater_than_65_or_less_than_18() {
        Employee employee = new Employee(null, "Tom", 16, "MALE", 20000.0);
        Employee employee2 = new Employee(null, "Tom", 16, "MALE", 20000.0);
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeMapper.toEntity(employeeService.createEmployee(employeeMapper.toRequest(employee))));
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeMapper.toEntity(employeeService.createEmployee(employeeMapper.toRequest(employee2))));
    }

    @Test
    void should_throw_exception_when_create_a_employee_of_age_greater_than_30_and_salary_less_than_20000() {
        Employee employee = new Employee(null, "Tom", 31, "MALE", 10000.0);
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeMapper.toEntity(employeeService.createEmployee(employeeMapper.toRequest(employee))));
    }

    @Test
    void should_active_true_when_create_a_employee() {
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee employeeResult = employeeMapper.toEntity(employeeService.createEmployee(employeeMapper.toRequest(employee)));
        assertTrue(employeeResult.isActive());
    }

    @Test
    void should_active_false_when_delete_a_employee() {
        Employee employee = new Employee(1, "Mike", 20, "MALE", 10000.0);
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        employeeService.deleteEmployee(1);
        verify(employeeRepository).save(employee);
    }

    @Test
    void should_throw_exception_when_update_a_employee_and_active_false() {
        Employee employee = new Employee(1, "Mike", 20, "MALE", 10000.0, false);
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        assertThrows(InvalidActiveEmployeeException.class, () -> employeeService.updateEmployee(1, employeeMapper.toRequest(employee)));
    }

}
