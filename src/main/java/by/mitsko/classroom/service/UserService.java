package by.mitsko.classroom.service;

import by.mitsko.classroom.entity.Report;
import by.mitsko.classroom.entity.Role;
import by.mitsko.classroom.entity.User;

import java.util.List;

public interface UserService {
    User signIn(String username, Role role, String email);

    void signOut(Long userId);

    User raiseHand(Long userId);

    List<User> getAuthorizedStudents();

    List<User> getAllStudents();

    void changeEmail(Long userId, String email);
}
