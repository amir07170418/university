package org.example.university.mapper;

import org.example.university.dto.StudentRequest;
import org.example.university.dto.StudentResponse;
import org.example.university.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "department.id",target = "departmentId")
    StudentResponse studentToResponse(Student student);
    @Mapping(target = "department",ignore = true)
    Student requestToStudent(StudentRequest studentRequest);
    @Mapping(target = "department",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role" ,ignore = true)
    void updateStudentFromRequest(StudentRequest studentRequest, @MappingTarget Student student);
}
