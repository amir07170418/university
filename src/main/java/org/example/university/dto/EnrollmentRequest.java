package org.example.university.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.university.model.Course;
import org.example.university.model.Student;

public class EnrollmentRequest {
    @NotNull
    private Double grade;
    @NotNull
    private Long courseId;
    @NotNull
    private Long studentId;
    public EnrollmentRequest() {}

    public EnrollmentRequest(Double grade, Long courseId, Long studentId) {
        this.grade = grade;
        this.courseId = courseId;
        this.studentId = studentId;
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
        return "EnrollmentRequest{" +
                "grade=" + grade +
                ", courseId=" + courseId +
                ", studentId=" + studentId +
                '}';
    }
}
