package com.example.demo.service;

import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import com.example.demo.repository.ICompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo.dto.mapper.CompanyMapper.toEntity;
import static com.example.demo.dto.mapper.CompanyMapper.toResponse;

@Service
public class CompanyService {
    private final ICompanyRepository companyRepository;

    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyResponse> getCompanies(Integer page, Integer size) {
        if (page == null || size == null) {
            return toResponse(companyRepository.findAll());
        } else {
            Pageable pageable = PageRequest.of(page - 1, size);
            return toResponse(companyRepository.findAll(pageable).toList());
        }
    }

    public CompanyResponse createCompany(Company company) {
        return toResponse(companyRepository.save(company));
    }

    public CompanyResponse updateCompany(int id, Company updatedCompany) {
        Company company = toEntity(getCompanyById(id));
        updatedCompany.setId(id);
        if (!Objects.isNull(updatedCompany.getName())) {
            company.setName(updatedCompany.getName());
        }
        return toResponse(companyRepository.save(updatedCompany));
    }

    public CompanyResponse getCompanyById(int id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with id: " + id);
        }
        return toResponse(company.get());
    }

    public void deleteCompany(int id) {
        getCompanyById(id);
        companyRepository.deleteById(id);
    }
}
