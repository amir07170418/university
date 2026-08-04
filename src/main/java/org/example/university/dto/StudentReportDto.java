package org.example.university.dto;

public class StudentReportDto {
    private String courseTitle;
    private Double grade;
    private Integer units;

    public StudentReportDto(String courseTitle, Double grade, Integer units) {
        this.courseTitle = courseTitle;
        this.grade = grade;
        this.units = units;
    }

    public StudentReportDto() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "courseTitle='" + courseTitle + '\'' +
                ", grade=" + grade +
                ", units=" + units ;
    }
}
