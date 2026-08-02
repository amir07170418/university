package org.example.university.dto;

import jakarta.validation.constraints.NotBlank;

public class DepartmentResponse {
    private Long id;
    private String name;

    public DepartmentResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DepartmentResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DepartmentResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
