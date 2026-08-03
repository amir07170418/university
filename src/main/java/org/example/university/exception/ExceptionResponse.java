package org.example.university.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String message;
    private int status;
    private LocalDateTime time;

    public ExceptionResponse(String message, int status, LocalDateTime time) {
        this.message = message;
        this.status = status;
        this.time = time;
    }

    public ExceptionResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
