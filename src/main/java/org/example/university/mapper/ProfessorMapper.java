package org.example.university.mapper;

import org.example.university.dto.ProfessorRequest;
import org.example.university.dto.ProfessorResponse;
import org.example.university.model.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    @Mapping(source = "department.id",target = "departmentId")
    ProfessorResponse toResponse(Professor professor);
    @Mapping(target = "department",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role" ,ignore = true)
    Professor toProfessor(ProfessorRequest professorRequest);
    @Mapping(target = "department",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role" ,ignore = true)
    void updateProfessorFromRequest(ProfessorRequest professorRequest, @MappingTarget Professor professor);
}
