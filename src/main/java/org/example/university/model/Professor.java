package org.example.university.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Entity
public class Professor extends User{
    @Column(unique = true)
    @NotBlank
    private String professorNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "professor")
    private Set<Course> courses;

    public Professor(Long id, String firstName, String lastName, String email, Role role, String password, String professorNumber, Department department, Set<Course> courses) {
        super(id, firstName, lastName, email, role, password);
        this.professorNumber = professorNumber;
        this.department = department;
        this.courses = courses;
    }

    public Professor() {
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getProfessorNumber() {
        return professorNumber;
    }

    public void setProfessorNumber(String professorNumber) {
        this.professorNumber = professorNumber;
    }
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Professor{" +
                super.toString() +
                ", professorNumber='" + professorNumber + '\'' +
                '}';
    }
}
