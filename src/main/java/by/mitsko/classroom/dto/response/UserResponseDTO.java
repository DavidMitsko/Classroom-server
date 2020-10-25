package by.mitsko.classroom.dto.response;

import by.mitsko.classroom.entity.User;
import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private boolean authorized;
    private boolean raisedHand;
    private String role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.authorized = user.isAuthorized();
        this.raisedHand = user.isRaisedHand();
        this.role = user.getRole().name();
    }
}
