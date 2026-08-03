package org.example.university.mapper;

import org.example.university.dto.EmployeeRequest;
import org.example.university.dto.EmployeeResponse;
import org.example.university.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponse toResponse(Employee employee);
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role",ignore = true)
    Employee toEmployee(EmployeeRequest employeeRequest);
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "role",ignore = true)
    void updateEmployeeFromRequest(EmployeeRequest employeeRequest, @MappingTarget Employee employee);
}
