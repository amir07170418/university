package org.example.university.repository;

import org.example.university.dto.ProfessorReportDto;
import org.example.university.model.Professor;
import org.example.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByEmail(String email);
    boolean existsByEmail(String email);
    Professor findByProfessorNumber(String professorNumber);
    boolean existsByProfessorNumber(String professorNumber);
    @Query("select count(p)>0 from Professor p where p.department.id=:id")
    boolean existsByDepartmentId(@Param("id") Long id);
    @Query("select new org.example.university.dto.ProfessorReportDto" +
            "(count(distinct c.id),count(distinct e.student.id),avg(e.grade))" +
            "from Enrollment e join e.course c where c.professor.id=:id")
    ProfessorReportDto getAverageGrade(@Param("id") Long id);
    @Query("select count(c)>0 from Course c where c.professor.id=:id")
    boolean professorHasCourse(@Param("id")  Long id);
}
