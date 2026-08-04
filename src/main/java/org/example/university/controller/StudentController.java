package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.StudentReporting;
import org.example.university.dto.StudentRequest;
import org.example.university.dto.StudentResponse;
import org.example.university.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StudentResponse register(@Valid @RequestBody StudentRequest studentRequest) {
        return  studentService.save(studentRequest);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public StudentResponse me() {
        return studentService.getMe();
    }
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable Long id) {
        return studentService.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<StudentResponse> findAll(@Valid Pageable pageable) {
        return studentService.findAll(pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Long id, @Valid @RequestBody StudentRequest studentRequest) {
        return studentService.update(id, studentRequest);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping
    public StudentResponse updateMe(@Valid @RequestBody StudentRequest studentRequest) {
        return  studentService.updateMe(studentRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/me/avg")
    public Double averageGrade() {
        return studentService.getMeAvg();
    }
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/report/{id}")
    public StudentReporting report(@PathVariable Long id) {
        return studentService.report(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/department/{id}")
    public Page<StudentResponse> findByDepartment(@PathVariable Long id,Pageable pageable) {
        return studentService.getStudentsByDepartmentId(id,pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/orderd/grade")
    public Page<StudentResponse> orderdByGrade(Pageable pageable) {
        return studentService.getStudentsOrderdByAvgGrade(pageable);
    }
}
