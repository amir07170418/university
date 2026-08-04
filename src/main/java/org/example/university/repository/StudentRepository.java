package org.example.university.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.university.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    boolean existsByEmail(String email);
    Student findByStudentNumber(String studentNumber);
    boolean existsByStudentNumber(String studentNumber);
    @Query("select s from Student s where s.department.id=:id")
    Page<Student> findByDepartmentId(@Param("id") Long id, Pageable pageable);
    @Query("select COUNT(s)>0 from Student s where s.department.id=:id")
    boolean existsByDepartmentId(@Param("id") Long id);


}
