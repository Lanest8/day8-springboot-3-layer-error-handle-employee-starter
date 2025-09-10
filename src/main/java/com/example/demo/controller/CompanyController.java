package com.example.demo.controller;

import com.example.demo.entity.Company;
import com.example.demo.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void empty() {
        companyService.deleteAllCompanies();
    }

    @GetMapping
    public List<Company> getCompanies(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return companyService.getCompanies(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company updateCompany(@PathVariable int id, @RequestBody Company updatedCompany) {
        return companyService.updateCompany(id, updatedCompany);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
    }
}
