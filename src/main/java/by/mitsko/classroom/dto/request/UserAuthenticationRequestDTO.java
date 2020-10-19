package by.mitsko.classroom.dto.request;

import lombok.Data;

@Data
public class UserAuthenticationRequestDTO {
    private String username;
    private String role;
    private String email;
}
