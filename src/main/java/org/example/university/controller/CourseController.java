package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.CourseRequest;
import org.example.university.dto.CourseResponse;
import org.example.university.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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
}
