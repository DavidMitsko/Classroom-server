package by.mitsko.classroom.dto.response;

import lombok.Data;

@Data
public class ErrorDTO {
    private String message;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public ErrorDTO(Throwable exception) {
        this.message = exception.getMessage();
    }
}
