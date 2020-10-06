package by.mitsko.classroom.controller.utils;

import by.mitsko.classroom.dto.response.UserResponseDTO;
import by.mitsko.classroom.entity.User;

import java.util.LinkedList;
import java.util.List;

public class Converter {
    public static List<UserResponseDTO> convertUserToUserResponse(List<User> users) {
        List<UserResponseDTO> response = new LinkedList<>();
        for(User user: users) {
            response.add(new UserResponseDTO(user));
        }
        return response;
    }
}
