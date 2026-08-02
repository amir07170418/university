package org.example.university.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.example.university.model.Course;
import org.example.university.model.Student;

public class EnrollmentResponse {
    private Long id;
    private Double grade;
    private Long courseId;
    private Long studentId;

    public EnrollmentResponse(Long id, Double grade, Long courseId, Long studentId) {
        this.id = id;
        this.grade = grade;
        this.courseId = courseId;
        this.studentId = studentId;
    }
    public EnrollmentResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "EnrollmentResponse{" +
                "id=" + id +
                ", grade=" + grade +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                '}';
    }
}
