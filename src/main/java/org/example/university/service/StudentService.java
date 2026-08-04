package org.example.university.service;

import org.example.university.dto.StudentReportDto;
import org.example.university.dto.StudentReporting;
import org.example.university.dto.StudentRequest;
import org.example.university.dto.StudentResponse;
import org.example.university.exception.DepartmentNotFoundException;
import org.example.university.exception.StudentAlreadyExistException;
import org.example.university.exception.StudentNotFoundException;
import org.example.university.exception.UserWithThisEmailExistException;
import org.example.university.mapper.StudentMapper;
import org.example.university.model.Department;
import org.example.university.model.Role;
import org.example.university.model.Student;
import org.example.university.repository.DepartmentRepository;
import org.example.university.repository.EnrollmentRepository;
import org.example.university.repository.StudentRepository;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements UniversityServices<StudentRequest, StudentResponse> {
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapper studentMapper;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(PasswordEncoder passwordEncoder, StudentRepository studentRepository, DepartmentRepository departmentRepository, StudentMapper studentMapper, EnrollmentRepository enrollmentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.studentMapper = studentMapper;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public StudentResponse save(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new StudentAlreadyExistException();
        }
        Student student=requestToStudent(request);
        studentRepository.save(student);
        return studentMapper.studentToResponse(student);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException());
        chekStudent(student,request);
        return updateStudent(student,request);
    }

    @Override
    public void delete(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException());
        studentRepository.delete(student);
    }

    @Override
    public StudentResponse findById(Long id) {
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException());
        return studentMapper.studentToResponse(student);
    }


    @Override
    public Page<StudentResponse> findAll(Pageable pageable) {
        Page<Student> students=studentRepository.findAll(pageable);
        return students.map(studentMapper::studentToResponse);
    }
    public StudentResponse getMe(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByEmail(email);
        if (student!=null){
            return studentMapper.studentToResponse(student);
        }
        throw new StudentNotFoundException();
    }
    public StudentResponse updateMe(StudentRequest request) {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByEmail(email);
        if (student!=null) {
            chekStudent(student,request);
            return updateStudent(student,request);
        }
        throw new StudentNotFoundException();
    }
    public Double getMeAvg(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByEmail(email);
        if (student!=null) {
            return enrollmentRepository.averageGrade(student.getId());
        }
        throw new StudentNotFoundException();
    }
    private Student requestToStudent(StudentRequest studentRequest) {
        Student student = studentMapper.requestToStudent(studentRequest);
        Department department = departmentRepository.findById(studentRequest.getDepartmentId())
                .orElseThrow(() -> new  DepartmentNotFoundException());
        student.setDepartment(department);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setRole(Role.STUDENT);
        return student;
    }
    public StudentReporting report(Long id){
        List<StudentReportDto> studentReports=enrollmentRepository.findByStudentId(id);
        StudentReporting reporting=new StudentReporting(enrollmentRepository.averageGrade(id),studentReports);
        return reporting;
    }
    public Page<StudentResponse> getStudentsByCourseId(Long id,Pageable pageable){
        Page<Student> students=enrollmentRepository.findByCourseId(id,pageable);
        return students.map(studentMapper::studentToResponse);
    }
    public Page<StudentResponse> getStudentsByDepartmentId(Long id,Pageable pageable){
        Page<Student> students=studentRepository.findByDepartmentId(id,pageable);
        return students.map(studentMapper::studentToResponse);
    }
    public Page<StudentResponse> getStudentsOrderdByAvgGrade(Pageable pageable){
        Page<Student> students=enrollmentRepository.findAllByOrderByGrade(pageable);
        return students.map(studentMapper::studentToResponse);
    }
    private void chekStudent(Student student,StudentRequest request) {
        if (!student.getEmail().equals(request.getEmail()) && studentRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (!student.getStudentNumber().equals(request.getStudentNumber()) &&
                studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new StudentAlreadyExistException();
        }
    }
    private StudentResponse updateStudent(Student student, StudentRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new DepartmentNotFoundException());
        studentMapper.updateStudentFromRequest(request,student);
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setDepartment(department);
        studentRepository.save(student);
        return studentMapper.studentToResponse(student);
    }

}



















