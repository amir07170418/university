package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.StudentRequest;
import org.example.university.dto.StudentResponse;
import org.example.university.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/register")
    public StudentResponse register(@Valid @RequestBody StudentRequest studentRequest) {
        return  studentService.save(studentRequest);
    }

}
