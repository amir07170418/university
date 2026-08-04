package org.example.university.dto;

public class ProfessorReportDto {
    private Long courses;
    private Long students;
    private Double averageGrade;

    public ProfessorReportDto(Long courses, Long students, Double averageGrade) {
        this.courses = courses;
        this.students = students;
        this.averageGrade = averageGrade;
    }

    public ProfessorReportDto() {
    }

    public Long getCourses() {
        return courses;
    }

    public void setCourses(Long courses) {
        this.courses = courses;
    }

    public Long getStudents() {
        return students;
    }

    public void setStudents(Long students) {
        this.students = students;
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return "ProfessorReportDto{" +
                "courses=" + courses +
                ", students=" + students +
                ", averageGrade=" + averageGrade +
                '}';
    }
}
