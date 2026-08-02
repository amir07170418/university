package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.*;
import org.example.university.security.JwtResponse;
import org.example.university.service.EmployeeService;
import org.example.university.service.ProfessorService;
import org.example.university.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final EmployeeService employeeService;

    public LoginController(StudentService studentService, ProfessorService professorService, EmployeeService employeeService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public StudentResponse register(@Valid @RequestBody StudentRequest studentRequest) {
        return   studentService.save(studentRequest);
    }
    @PostMapping("/register")
    public ProfessorResponse register(@Valid @RequestBody ProfessorRequest professorRequest) {
        return professorService.save(professorRequest);
    }
    @PostMapping("/register")
    public EmployeeResponse  register(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.save(employeeRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(){

    }


}
