package com.example.demo.dto.mapper;

import com.example.demo.dto.CompanyResponse;
import com.example.demo.entity.Company;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class CompanyMapper {

    public static CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        return companyResponse;
    }

    public static List<CompanyResponse> toResponse(List<Company> companies) {
        return companies.stream().map(CompanyMapper::toResponse).toList();
    }

    public static Company toEntity(CompanyResponse companyResponse) {
        Company company = new Company();
        BeanUtils.copyProperties(companyResponse, company);
        return company;
    }
}
