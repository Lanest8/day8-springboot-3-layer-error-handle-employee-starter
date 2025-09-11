package com.example.demo.repository;

import com.example.demo.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findEmployeesByGender(String gender);

    List<Employee> findEmployeesByGender(String gender, Pageable pageable);
}
