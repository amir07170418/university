package org.example.university.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
public class Student extends  User{
    @NotBlank
    @Column(unique = true)
    private String studentNumber;
    private Integer entranceYear;
    @OneToMany(fetch =  FetchType.LAZY,mappedBy = "student")
    private Set<Enrollment> enrollments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public Student(Long id, String firstName, String lastName, String email, String studentNumber, Integer entersYear
            , String password, Set<Enrollment> enrollments, Department department,Role role) {
        super(id,firstName,lastName,email,role,password);
        this.enrollments = enrollments;
        this.department = department;
        this.studentNumber = studentNumber;
        this.entranceYear = entersYear;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Student() {
    }
    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getEntranceYear() {
        return entranceYear;
    }

    public void setEntranceYear(Integer entranceYear) {
        this.entranceYear = entranceYear;
    }

    @Override
    public String toString() {
        return "Student{" +
                super.toString() +
                "studentNumber='" + studentNumber +
                ", entranceYear=" + entranceYear +
                ", enrollments=" + enrollments +
                '}';
    }
}
