package by.mitsko.classroom.service;

import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.exception.InvalidDataException;

import java.util.List;

public interface UserService {
    User signIn(String username);

    void signOut(Long userId);

    User raiseHand(Long userId);

    List<User> getAllUsers();
}
