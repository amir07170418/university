package org.example.university.repository;

import org.example.university.model.Professor;
import org.example.university.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByEmail(String email);
    boolean existsByEmail(String email);
    Professor findByProfessorNumber(String professorNumber);
    boolean existsByProfessorNumber(String professorNumber);
}
