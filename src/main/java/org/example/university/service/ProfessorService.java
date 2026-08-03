package org.example.university.service;

import org.example.university.dto.ProfessorRequest;
import org.example.university.dto.ProfessorResponse;
import org.example.university.exception.*;
import org.example.university.mapper.ProfessorMapper;
import org.example.university.model.Department;
import org.example.university.model.Professor;
import org.example.university.model.Role;
import org.example.university.repository.DepartmentRepository;
import org.example.university.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService implements UniversityServices<ProfessorRequest, ProfessorResponse> {
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, PasswordEncoder passwordEncoder, DepartmentRepository departmentRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
        this.professorMapper = professorMapper;
    }

    @Override
    public ProfessorResponse save(ProfessorRequest request) {
        if (professorRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (professorRepository.existsByProfessorNumber(request.getProfessorNumber())) {
            throw new ProfessorAlreadyExistExceptions();
        }
        Professor professor = requestToProfessor(request);
        professorRepository.save(professor);
        return  professorMapper.toResponse(professor);
    }

    @Override
    public ProfessorResponse update(Long id, ProfessorRequest request) {
        Professor professor = professorRepository.findById(id).orElseThrow(ProfessorNotFoundException::new);
        professorCheck(professor, request);
        return updateProfessor(professor, request);
    }

    @Override
    public void delete(Long id) {
        Professor  professor = professorRepository.findById(id).orElseThrow(ProfessorNotFoundException::new);
        professorRepository.delete(professor);
    }

    @Override
    public ProfessorResponse findById(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(ProfessorNotFoundException::new);
        return  professorMapper.toResponse(professor);
    }

    @Override
    public Page<ProfessorResponse> findAll(Pageable pageable) {
        Page<Professor> professors = professorRepository.findAll(pageable);
        return professors.map(professorMapper::toResponse);
    }
    public ProfessorResponse getMe(){
        String  email = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor professor = professorRepository.findByEmail(email);
        if (professor != null) {
            return   professorMapper.toResponse(professor);
        }
        throw new ProfessorNotFoundException();
    }
    public ProfessorResponse updateMe(ProfessorRequest request){
        String  email = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor professor = professorRepository.findByEmail(email);
        if (professor != null) {
            professorCheck(professor, request);
            return updateProfessor(professor, request);
        }
        throw new ProfessorNotFoundException();
    }

    private void professorCheck(Professor professor,ProfessorRequest request) {
        if (!professor.getEmail().equals(request.getEmail()) && professorRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (!professor.getProfessorNumber().equals(request.getProfessorNumber()) &&
                professorRepository.existsByProfessorNumber(request.getProfessorNumber())) {
            throw  new ProfessorAlreadyExistExceptions();
        }
    }
    private ProfessorResponse updateProfessor(Professor professor, ProfessorRequest request) {
        Department department=departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(DepartmentNotFoundException::new);
        professorMapper.updateProfessorFromRequest(request,professor);
        professor.setPassword(passwordEncoder.encode(request.getPassword()));
        professor.setDepartment(department);
        professorRepository.save(professor);
        return  professorMapper.toResponse(professor);
    }
    private Professor requestToProfessor(ProfessorRequest request) {
        Professor professor = professorMapper.toProfessor(request);
        Department  department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(DepartmentNotFoundException::new);
        professor.setPassword(passwordEncoder.encode(request.getPassword()));
        professor.setDepartment(department);
        professor.setRole(Role.PROFESSOR);
        return professor;
    }
}















