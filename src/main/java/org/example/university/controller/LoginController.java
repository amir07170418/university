package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.*;
import org.example.university.security.JwtResponse;
import org.example.university.service.EmployeeService;
import org.example.university.service.ProfessorService;
import org.example.university.service.StudentService;
import org.example.university.service.UserService;
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
    private final UserService userService;

    public LoginController(StudentService studentService, ProfessorService professorService, EmployeeService employeeService, UserService userService) {
        this.studentService = studentService;
        this.professorService = professorService;
        this.employeeService = employeeService;
        this.userService = userService;
    }
    @PostMapping("/register/student")
    public StudentResponse register(@Valid @RequestBody StudentRequest studentRequest) {
        return   studentService.save(studentRequest);
    }
    @PostMapping("/register/professor")
    public ProfessorResponse register(@Valid @RequestBody ProfessorRequest professorRequest) {
        return professorService.save(professorRequest);
    }
    @PostMapping("/register/employee")
    public EmployeeResponse  register(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.save(employeeRequest);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserRequest userRequest) {
        return userService.login(userRequest);
    }


}
