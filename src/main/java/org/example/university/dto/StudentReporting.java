package org.example.university.dto;

import java.util.List;

public class StudentReporting {
    private Double averageGrade;
    private List<StudentReportDto> studentReports;
    public StudentReporting(Double averageGrade, List<StudentReportDto> studentReports) {
        this.averageGrade = averageGrade;
        this.studentReports = studentReports;
    }

    public StudentReporting() {
    }

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<StudentReportDto> getStudentReports() {
        return studentReports;
    }

    public void setStudentReports(List<StudentReportDto> studentReports) {
        this.studentReports = studentReports;
    }

    @Override
    public String toString() {
        return "studentReporting{" +
                "averageGrade=" + averageGrade +
                ", studentReports=" + studentReports +
                '}';
    }

}
