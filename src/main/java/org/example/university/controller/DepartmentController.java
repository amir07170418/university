package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.DepartmentRequest;
import org.example.university.dto.DepartmentResponse;
import org.example.university.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping
    public Page<DepartmentResponse> findAll(@Valid Pageable pageable) {
        return departmentService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DepartmentResponse save(@Valid @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.save(departmentRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public DepartmentResponse update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.update(id, departmentRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }
}
