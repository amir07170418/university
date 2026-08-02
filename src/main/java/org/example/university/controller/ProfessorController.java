package org.example.university.controller;

import org.example.university.dto.ProfessorRequest;
import org.example.university.dto.ProfessorResponse;
import org.example.university.service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<ProfessorResponse> findAll(Pageable pageable) {
        return professorService.findAll(pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ProfessorResponse findById(@PathVariable Long id) {
        return professorService.findById(id);
    }
    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping
    public ProfessorResponse getMe(){
        return  professorService.getMe();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProfessorResponse create(@RequestBody ProfessorRequest professorRequest) {
        return professorService.save(professorRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProfessorResponse update(@PathVariable Long id, @RequestBody ProfessorRequest professorRequest) {
        return professorService.update(id, professorRequest);
    }
    @PreAuthorize("hasRole('PROFESSOR')")
    @PutMapping
    public ProfessorResponse updateMe(@RequestBody ProfessorRequest professorRequest) {
        return professorService.updateMe(professorRequest);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        professorService.delete(id);
    }
}
