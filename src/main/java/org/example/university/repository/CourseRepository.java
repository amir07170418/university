package org.example.university.repository;

import org.example.university.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);
    boolean existsByCode(String code);
    @Query("select c from Course c join c.professor p where p.id=:id")
    Page<Course> findByProfessor(@Param("id") Long id, Pageable pageable);
    @Query("select count(c)>=5 from Course c where c.professor.id=:id")
    boolean hasMaxCourse(@Param("id") Long id);

}
