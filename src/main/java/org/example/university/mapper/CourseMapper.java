package org.example.university.mapper;

import org.example.university.dto.CourseRequest;
import org.example.university.dto.CourseResponse;
import org.example.university.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "professor.id",target = "professorId")
    CourseResponse toResponse(Course course);
    @Mapping(target = "professor",ignore = true)
    Course toCourse(CourseRequest courseRequest);
    @Mapping(target = "professor",ignore = true)
    void updateCourseFromRequest(CourseRequest courseRequest, @MappingTarget Course course);
}
