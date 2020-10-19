package by.mitsko.classroom.dto.response;

import by.mitsko.classroom.entity.Log;
import lombok.Data;

@Data
public class LogResponseDTO {
    private String username;
    private String action;
    private String date;

    public LogResponseDTO(Log log) {
        this.username = log.getUser().getUsername();
        this.action = log.getAction().name();
        this.date = log.getDate().toString();
    }
}
