package org.example.university.service;

import jakarta.transaction.Transactional;
import org.example.university.dto.EmployeeRequest;
import org.example.university.dto.EnrollmentRequest;
import org.example.university.dto.EnrollmentResponse;
import org.example.university.exception.*;
import org.example.university.model.*;
import org.example.university.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService  {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;

    public EnrollmentService(StudentRepository studentRepository, CourseRepository courseRepository
            , EnrollmentRepository enrollmentRepository, ProfessorRepository professorRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.professorRepository = professorRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public EnrollmentResponse save(EnrollmentRequest request) {
        if (enrollmentRepository.existsByCourseIdAndStudentId(request.getCourseId(), request.getStudentId())) {
            throw new EnrollmentAlreadyExistException();
        }
        if (enrollmentRepository.capacityFull(request.getCourseId())) {
            throw new CourseFullCapacityException();
        }
        Enrollment enrollment = new Enrollment();
        Student student = studentRepository.findById(request.getStudentId()).orElseThrow(StudentNotFoundException::new);
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow(CourseNotFoundException::new);
        if (enrollmentRepository.sumUnits(student.getId())+course.getUnits()>20)
            throw new MaximumUnitException();
        enrollment.setCourse(course);
        enrollment.setStudent(student);
        enrollment.setGrade(request.getGrade());
        enrollmentRepository.save(enrollment);
        return enrollmentToResponse(enrollment);
    }
    @Transactional
    public EnrollmentResponse saveMe(Long courseId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student=studentRepository.findByEmail(email);
        if (student==null){
            throw new StudentNotFoundException();
        }
        EnrollmentRequest enrollmentRequest = new EnrollmentRequest();
        enrollmentRequest.setCourseId(courseId);
        enrollmentRequest.setStudentId(student.getId());
        return  save(enrollmentRequest);
    }
    @Transactional
    public void delete(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFoundException::new);
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getRole() == Role.ADMIN) {
                enrollmentRepository.delete(enrollment);
                return;
            }else  if (user.getRole() == Role.STUDENT) {
                if (user.getEmail().equals(enrollment.getStudent().getEmail())) {
                    enrollmentRepository.delete(enrollment);
                    return;
                }
            }
        }
        throw new EnrollmentAccessDeniedException();
    }

    public EnrollmentResponse findById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFoundException::new);
        return enrollmentToResponse(enrollment);
    }
    public Page<EnrollmentResponse>findAllMe(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Page<Enrollment> page= enrollmentRepository.findAllByEmail(user.getEmail(),pageable);
            return page.map(this::enrollmentToResponse);
        }
        throw new StudentNotFoundException();
    }

    public Page<EnrollmentResponse> findAll(Pageable pageable) {
        Page<Enrollment> enrollments = enrollmentRepository.findAll(pageable);
        return enrollments.map(this::enrollmentToResponse);
    }
    @Transactional
    public EnrollmentResponse updateGrade(Long id,Double grade) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(EnrollmentNotFoundException::new);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor professor=professorRepository.findByEmail(username);
        if (professor!=null) {
            if (enrollment.getCourse().getProfessor().getId().equals(professor.getId())) {
                enrollment.setGrade(grade);
                enrollmentRepository.save(enrollment);
                return enrollmentToResponse(enrollment);
            }
        }
        throw new GradeAccessDeniedException();
    }

    private EnrollmentResponse enrollmentToResponse(Enrollment enrollment) {
        EnrollmentResponse enrollmentResponse = new EnrollmentResponse();
        enrollmentResponse.setId(enrollment.getId());
        enrollmentResponse.setCourseId(enrollment.getCourse().getId());
        enrollmentResponse.setStudentId(enrollment.getStudent().getId());
        enrollmentResponse.setGrade(enrollment.getGrade());
        return enrollmentResponse;
    }
}
