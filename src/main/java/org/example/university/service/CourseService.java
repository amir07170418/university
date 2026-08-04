package org.example.university.service;

import org.example.university.dto.CourseRequest;
import org.example.university.dto.CourseResponse;
import org.example.university.exception.CourseAlreadyExistExceptions;
import org.example.university.exception.CourseNotFoundException;
import org.example.university.exception.ProfessorNotFoundException;
import org.example.university.model.Course;
import org.example.university.model.Professor;
import org.example.university.repository.CourseRepository;
import org.example.university.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements UniversityServices<CourseRequest, CourseResponse> {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public CourseService(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public CourseResponse save(CourseRequest request) {
        if (courseRepository.existsByCode(request.getCode())) {
            throw new CourseAlreadyExistExceptions();
        }
        Course course = new Course();
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(ProfessorNotFoundException::new);
        course.setCode(request.getCode());
        course.setCapacity(request.getCapacity());
        course.setTitle(request.getTitle());
        course.setUnits(request.getUnits());
        course.setProfessor(professor);
        courseRepository.save(course);
        return courseToResponse(course);
    }

    @Override
    public CourseResponse update(Long id, CourseRequest request) {
        Course course=courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        if (!course.getCode().equals(request.getCode()) && courseRepository.existsByCode(request.getCode())) {
            throw new CourseAlreadyExistExceptions();
        }
        Professor professor = professorRepository.findById(request.getProfessorId())
                .orElseThrow(ProfessorNotFoundException::new);
        course.setTitle(request.getTitle());
        course.setUnits(request.getUnits());
        course.setCapacity(request.getCapacity());
        course.setProfessor(professor);
        course.setCode(request.getCode());
        courseRepository.save(course);
        return courseToResponse(course);
    }

    @Override
    public void delete(Long id) {
        Course course=courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        courseRepository.delete(course);
    }

    @Override
    public CourseResponse findById(Long id) {
        Course course=courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        return courseToResponse(course);
    }

    @Override
    public Page<CourseResponse> findAll(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.map(this::courseToResponse);
    }
    public Page<CourseResponse> listCourseByProfessor(Long professorId,Pageable pageable) {
        Page<Course> courses=courseRepository.findByProfessor(professorId,pageable);
        return courses.map(this::courseToResponse);
    }
    private CourseResponse courseToResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCode(course.getCode());
        courseResponse.setCapacity(course.getCapacity());
        courseResponse.setTitle(course.getTitle());
        courseResponse.setUnits(course.getUnits());
        courseResponse.setProfessorId(course.getProfessor().getId());
        return courseResponse;
    }
}
