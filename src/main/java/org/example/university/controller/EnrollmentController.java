package org.example.university.controller;

import org.example.university.dto.EnrollmentRequest;
import org.example.university.dto.EnrollmentResponse;
import org.example.university.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private  final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public EnrollmentResponse save(@RequestBody EnrollmentRequest enrollmentRequest) {
        return enrollmentService.save(enrollmentRequest);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me/{id}")
    public EnrollmentResponse saveMe(@PathVariable Long id) {
        return  enrollmentService.saveMe(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<EnrollmentResponse> findAll(Pageable pageable) {
        return enrollmentService.findAll(pageable);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public Page<EnrollmentResponse> findAllMe(Pageable pageable) {
        return enrollmentService.findAllMe(pageable);
    }
    @GetMapping("/{id}")
    public EnrollmentResponse findById(@PathVariable Long id) {
        return enrollmentService.findById(id);
    }
    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping("/{id}")
    public EnrollmentResponse updateGrade(@PathVariable Long id , @RequestParam Double grade) {
        return enrollmentService.updateGrade(id, grade);
    }
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        enrollmentService.delete(id);
    }

}
