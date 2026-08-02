package org.example.university.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CourseRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String code;
    @NotNull
    private Integer units;
    @NotNull
    private Integer capacity;
    @NotNull
    private Long professorId;

    public CourseRequest(String title, String code, Integer units, Integer capacity, Long professorId) {
        this.title = title;
        this.code = code;
        this.units = units;
        this.capacity = capacity;
        this.professorId = professorId;
    }

    public CourseRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "CourseRequest{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", units=" + units +
                ", capacity=" + capacity +
                ", professorId=" + professorId +
                '}';
    }
}
