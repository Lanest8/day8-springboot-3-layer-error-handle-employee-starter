package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class CompanyRequest {
    @NotNull(message = "name cannot be null")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
