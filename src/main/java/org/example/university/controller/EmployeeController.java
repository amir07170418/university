package org.example.university.controller;

import jakarta.validation.Valid;
import org.example.university.dto.EmployeeRequest;
import org.example.university.dto.EmployeeResponse;
import org.example.university.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public EmployeeResponse findById(@PathVariable Long id) {
        return employeeService.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return employeeService.findAll(pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public  EmployeeResponse save(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.save(employeeRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id,@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(id, employeeRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

}
