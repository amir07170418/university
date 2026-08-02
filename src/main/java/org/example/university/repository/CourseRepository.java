package org.example.university.repository;

import org.example.university.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCode(String code);
    boolean existsByCode(String code);
}
