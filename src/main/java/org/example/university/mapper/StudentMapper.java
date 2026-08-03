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
    Student requestToStudent(StudentRequest studentRequest);
    void updateStudentFromRequest(StudentRequest studentRequest, @MappingTarget Student student);
}
