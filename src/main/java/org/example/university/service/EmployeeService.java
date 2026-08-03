package org.example.university.service;

import org.example.university.dto.EmployeeRequest;
import org.example.university.dto.EmployeeResponse;

import org.example.university.exception.EmployeeAlreadyExistException;
import org.example.university.exception.EmployeeNotFoundException;
import org.example.university.exception.UserWithThisEmailExistException;
import org.example.university.model.Employee;
import org.example.university.model.Role;
import org.example.university.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements  UniversityServices<EmployeeRequest, EmployeeResponse> {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (employeeRepository.existsByEmployeeNumber(request.getEmployeeNumber())) {
            throw new EmployeeAlreadyExistException();
        }
        Employee employee = requestToEmployee(request);
        employeeRepository.save(employee);
        return employeeToResponse(employee);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        if (!employee.getEmail().equals(request.getEmail()) && employeeRepository.existsByEmail(request.getEmail())) {
            throw new UserWithThisEmailExistException();
        }
        if (!employee.getEmployeeNumber().equals(request.getEmployeeNumber()) &&
                employeeRepository.existsByEmployeeNumber(request.getEmployeeNumber())) {
            throw new EmployeeAlreadyExistException();
        }
        employee.setEmail(request.getEmail());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmployeeNumber(employee.getEmployeeNumber());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setId(id);
        employeeRepository.save(employee);
        return employeeToResponse(employee);
    }

    @Override
    public void delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
        return employeeToResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(this::employeeToResponse);
    }
    private Employee requestToEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setRole(Role.ADMIN);
        employee.setEmployeeNumber(request.getEmployeeNumber());
        return employee;
    }
    private EmployeeResponse employeeToResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setEmployeeNumber(employee.getEmployeeNumber());
        return employeeResponse;
    }
}










