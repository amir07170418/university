package org.example.university.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Employee extends User {
    @NotBlank
    @Column(unique = true)
    private String employeeNumber;

    public Employee(Long id, String firstName, String lastName, String email, Role role, String password, String employeeNumber) {
        super(id, firstName, lastName, email, role, password);
        this.employeeNumber = employeeNumber;
    }

    public Employee() {
    }


    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }


    @Override
    public String toString() {
        return "Employee{" +
                super.toString() +
                ", employeeNumber='" + employeeNumber + '\'' +
                '}';
    }
}
