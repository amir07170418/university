package org.example.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CourseAlreadyExistExceptions.class)
    public ResponseEntity<ExceptionResponse>  handleCourseAlreadyExistExceptions(CourseAlreadyExistExceptions e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createExceptionResponse(HttpStatus.BAD_REQUEST,"course already exists"));
    }
    @ExceptionHandler(CourseFullCapacityException.class)
    public ResponseEntity<ExceptionResponse> handleCourseFullCapacityException(CourseFullCapacityException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"course full capacity"));
    }
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCourseNotFoundException(CourseNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"course not found"));
    }
    @ExceptionHandler(DepartmentAlreadyExist.class)
    public ResponseEntity<ExceptionResponse> handleDepartmentAlreadyExist(DepartmentAlreadyExist e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"department already exist"));
    }
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDepartmentNotFoundException(DepartmentNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"department not found"));
    }
    @ExceptionHandler(EmailOrPasswordInCorrectException.class)
    public ResponseEntity<ExceptionResponse> handleEmailOrPasswordInCorrectException(EmailOrPasswordInCorrectException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"email or password incorrect"));
    }
    @ExceptionHandler(EmployeeAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleEmployeeAlreadyExistException(EmployeeAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"employee already exist"));
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEmployeeNotFoundException(EmployeeNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"employee not found"));
    }
    @ExceptionHandler(EnrollmentAccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleEnrollmentAccessDeniedException(EnrollmentAccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(createExceptionResponse
                (HttpStatus.FORBIDDEN,"enrollment access denied"));
    }
    @ExceptionHandler(EnrollmentAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleEnrollmentAlreadyExistException(EnrollmentAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"enrollment already exist"));
    }
    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEnrollmentNotFoundException(EnrollmentNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"enrollment not found"));
    }
    @ExceptionHandler(GradeAccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleGradeAccessDeniedException(GradeAccessDeniedException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(createExceptionResponse
                (HttpStatus.FORBIDDEN,"grade access denied"));
    }
    @ExceptionHandler(ProfessorAlreadyExistExceptions.class)
    public ResponseEntity<ExceptionResponse> handleProfessorAlreadyExistException(ProfessorAlreadyExistExceptions e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"professor already exist"));
    }
    @ExceptionHandler(ProfessorNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProfessorNotFoundException(ProfessorNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"professor not found"));
    }
    @ExceptionHandler(StudentAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleStudentAlreadyExistException(StudentAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"student already exist"));
    }
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleStudentNotFoundException(StudentNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse
                (HttpStatus.NOT_FOUND,"student not found"));
    }
    @ExceptionHandler(UserWithThisEmailExistException.class)
    public ResponseEntity<ExceptionResponse> handleUserWithThisEmailExistException(UserWithThisEmailExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse
                (HttpStatus.BAD_REQUEST,"user with this email already exist"));
    }









    private ExceptionResponse createExceptionResponse(HttpStatus status, String message){
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(status.value());
        exceptionResponse.setMessage(message);
        exceptionResponse.setTime(LocalDateTime.now());
        return exceptionResponse;
    }
}
