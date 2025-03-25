package com.finwise.api.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDTO {
    @NotBlank(message = "Category name is required")
    private String name;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}