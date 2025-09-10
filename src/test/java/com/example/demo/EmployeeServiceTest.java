package com.example.demo;

import com.example.demo.entity.Employee;
import com.example.demo.exception.InvalidAgeEmployeeException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void should_throw_exception_when_create_a_employee() {
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.createEmployee(employee)).thenReturn(employee);
        Employee employeeResult = employeeService.createEmployee(employee);
        assertEquals(employee, employeeResult);
    }

    @Test
    void should_throw_exception_when_create_a_employee_of_greater_than_65_or_less_than_18() {
        Employee employee = new Employee(null, "Tom", 16, "MALE", 20000.0);
        Employee employee2 = new Employee(null, "Tom", 16, "MALE", 20000.0);
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeService.createEmployee(employee));
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeService.createEmployee(employee2));
    }

    @Test
    void should_throw_exception_when_create_a_employee_of_age_greater_than_30_and_salary_less_than_20000() {
        Employee employee = new Employee(null, "Tom", 31, "MALE", 10000.0);
        assertThrows(InvalidAgeEmployeeException.class, () -> employeeService.createEmployee(employee));
    }

    @Test
    void should_active_true_when_create_a_employee() {
        Employee employee = new Employee(null, "Tom", 20, "MALE", 20000.0);
        when(employeeRepository.createEmployee(employee)).thenReturn(employee);
        Employee employeeResult = employeeService.createEmployee(employee);
        assertTrue(employeeResult.isActiveStatus());
    }

    @Test
    void should_active_false_when_delete_a_employee() {
        Employee employee = new Employee(1, "Mike", 20, "MALE", 10000.0);
        when(employeeRepository.getEmployeeById(anyInt())).thenReturn(employee);
        employeeService.deleteEmployee(1);
        verify(employeeRepository).deleteEmployee(1);
    }

}
