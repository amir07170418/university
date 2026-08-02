package org.example.university.dto;


public class CourseResponse {
    private Long id;
    private String title;
    private String code;
    private Integer units;
    private Integer capacity;
    private Long professorId;

    public CourseResponse(Long id, String title, String code, Integer units, Integer capacity, Long professorId) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.units = units;
        this.capacity = capacity;
        this.professorId = professorId;
    }

    public CourseResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", units=" + units +
                ", capacity=" + capacity +
                ", professorId=" + professorId +
                '}';
    }
}
