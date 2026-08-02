package org.example.university.service;

import org.example.university.dto.StudentRequest;
import org.example.university.dto.StudentResponse;
import org.example.university.exception.DepartmentNotFoundException;
import org.example.university.exception.StudentAlreadyExistException;
import org.example.university.exception.StudentNotFoundException;
import org.example.university.exception.UserWithThisEmailExistException;
import org.example.university.model.Department;
import org.example.university.model.Role;
import org.example.university.model.Student;
import org.example.university.repository.DepartmentRepository;
import org.example.university.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements UniversityServices<StudentRequest, StudentResponse> {
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public StudentService(PasswordEncoder passwordEncoder, StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.passwordEncoder = passwordEncoder;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
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
        return studentToResponse(student);
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
        return studentToResponse(student);
    }


    @Override
    public Page<StudentResponse> findAll(Pageable pageable) {
        Page<Student> students=studentRepository.findAll(pageable);
        return students.map(this::studentToResponse);
    }
    public StudentResponse getMe(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByEmail(email);
        if (student!=null){
            return studentToResponse(student);
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
    private Student requestToStudent(StudentRequest studentRequest) {
        Student student = new Student();
        Department department = departmentRepository.findById(studentRequest.getDepartmentId())
                .orElseThrow(() -> new  DepartmentNotFoundException());
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setStudentNumber(studentRequest.getStudentNumber());
        student.setEmail(studentRequest.getEmail());
        student.setDepartment(department);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setRole(Role.STUDENT);
        student.setEntranceYear(studentRequest.getEntranceYear());
        return student;
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
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setStudentNumber(request.getStudentNumber());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setEntranceYear(request.getEntranceYear());
        student.setDepartment(department);
        studentRepository.save(student);
        return studentToResponse(student);
    }
    private StudentResponse studentToResponse(Student student) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(student.getId());
        studentResponse.setFirstName(student.getFirstName());
        studentResponse.setLastName(student.getLastName());
        studentResponse.setStudentNumber(student.getStudentNumber());
        studentResponse.setEmail(student.getEmail());
        studentResponse.setDepartmentId(student.getDepartment().getId());
        studentResponse.setEntranceYear(student.getEntranceYear());
        return studentResponse;
    }
}



















