package org.example.university.repository;

import org.example.university.dto.StudentReportDto;
import org.example.university.model.Course;
import org.example.university.model.Enrollment;
import org.example.university.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("select count(e)>0 from Enrollment e where e.course.id=:courseId and e.student.id=:studentId")
    boolean existsByCourseIdAndStudentId(Long courseId, Long studentId);
    @Query("select count(e)>=c.capacity from Enrollment e join e.course c where c.id=:id")
    boolean capacityFull(Long id);
    @Query("select e from Enrollment e join e.student s where s.email=:email")
    Page<Enrollment>  findAllByEmail(@Param("email") String email, Pageable pageable);
    @Query("select avg(e.grade) from Enrollment e join e.student s where s.id=:id")
    Double averageGrade(@Param("id") Long id);
    @Query("select s from Enrollment e join  e.student s where e.course.id=:id")
    Page<Student> findByCourseId(@Param("id") Long id,Pageable pageable);
    @Query("select s from Enrollment e join  e.student s group by s order by avg(e.grade) desc ")
    Page<Student> findAllByOrderByGrade(Pageable pageable);
    @Query("select  new org.example.university.dto.StudentReportDto(c.title,e.grade,c.units) from Enrollment e  join e.course c where e.student.id=:id")
    List<StudentReportDto> findByStudentId(@Param("id") Long id);
}
