package by.mitsko.classroom.controller.utils;

import by.mitsko.classroom.dto.response.LogResponseDTO;
import by.mitsko.classroom.dto.response.UserResponseDTO;
import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Log;
import by.mitsko.classroom.entity.User;

import java.util.Arrays;
import java.util.Collections;
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

    public static List<LogResponseDTO> convertLogToLogResponse(List<Log> logs) {
        List<LogResponseDTO> response = new LinkedList<>();
        for(Log log: logs) {
            response.add(new LogResponseDTO(log));
        }
        return response;
    }
}
