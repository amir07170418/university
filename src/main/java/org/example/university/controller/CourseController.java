package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.CourseRequest;
import org.example.university.dto.CourseResponse;
import org.example.university.dto.StudentResponse;
import org.example.university.service.CourseService;
import org.example.university.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final StudentService studentService;
    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }
    @GetMapping
    public Page<CourseResponse> findAll(@Valid Pageable pageable) {
        return courseService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return courseService.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CourseResponse save(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.save(courseRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest courseRequest) {
        return courseService.update(id, courseRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list/{id}")
    public Page<CourseResponse> findByProfessorId(@PathVariable Long id,Pageable pageable) {
        return courseService.listCourseByProfessor(id,pageable);
    }
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSOR')")
    @GetMapping("/students/{id}")
    public Page<StudentResponse> findByStudentId(@PathVariable Long id,Pageable pageable) {
        return studentService.getStudentsByCourseId(id,pageable);
    }
}
