package org.example.university.service;

import jakarta.transaction.Transactional;
import org.example.university.dto.DepartmentRequest;
import org.example.university.dto.DepartmentResponse;
import org.example.university.exception.DepartmentAlreadyExist;
import org.example.university.exception.DepartmentNotFoundException;
import org.example.university.model.Department;
import org.example.university.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements UniversityServices<DepartmentRequest, DepartmentResponse> {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    @Transactional
    @Override
    public DepartmentResponse save(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new DepartmentAlreadyExist();
        }
        Department department = new Department();
        department.setName(request.getName());
        departmentRepository.save(department);
        return departmentToResponse(department);
    }
    @Transactional
    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
        if (!department.getName().equals(request.getName()) && departmentRepository.existsByName(request.getName())) {
            throw new DepartmentAlreadyExist();
        }
        department.setName(request.getName());
        department.setId(id);
        departmentRepository.save(department);
        return departmentToResponse(department);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
        departmentRepository.delete(department);
    }

    @Override
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
        return departmentToResponse(department);
    }

    @Override
    public Page<DepartmentResponse> findAll(Pageable pageable) {
        Page<Department> departments = departmentRepository.findAll(pageable);
        return departments.map(this::departmentToResponse);
    }
    private DepartmentResponse departmentToResponse(Department department) {
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId(department.getId());
        departmentResponse.setName(department.getName());
        return departmentResponse;
    }
}
