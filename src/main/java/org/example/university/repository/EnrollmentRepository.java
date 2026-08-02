package org.example.university.repository;

import org.example.university.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("select count(e)>0 from Enrollment e where e.course.id=:courseId and e.student.id=:studentId")
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
    @Query("select count(e)>=c.capacity from Enrollment e join e.course c where c.id=:id")
    boolean capacityFull(Long id);
}
