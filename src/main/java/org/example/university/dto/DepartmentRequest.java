package org.example.university.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {
    @NotBlank
    private String name;

    public DepartmentRequest(String name) {
        this.name = name;
    }
    public DepartmentRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DepartmentRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
