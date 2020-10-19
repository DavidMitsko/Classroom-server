package by.mitsko.classroom.service.impl;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Log;
import by.mitsko.classroom.entity.Role;
import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.repository.LogRepository;
import by.mitsko.classroom.repository.UserRepository;
import by.mitsko.classroom.service.UserService;
import by.mitsko.classroom.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LogRepository logRepository) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    @Override
    public User signIn(String username, Role role, String email) {
        Validator.validateUsername(username);

        User user = userRepository.getByUsername(username);;
        if (user != null) {
            if (user.isAuthorized()) {
                throw new AccessDeniedException("This user is already authorized");
            } else {
                user.setAuthorized(true);
                user.setRaisedHand(false);
            }
        } else {
            if (role == Role.STUDENT) {
                user = new User(username, role);
            }
            if (role == Role.TEACHER) {
                user = new User(username, email, role);
            }
        }

        saveUser(user);
        if (user.getRole() == Role.STUDENT) {
            saveLog(new Log(Action.SIGN_IN, user));
        }

        return user;
    }

    @Override
    public void signOut(Long userId) {
        User user = userRepository.getById(userId);
        Validator.validateAuthorizedUser(user);

        if (user.getRole() == Role.STUDENT) {
            if(user.isRaisedHand()) {
                saveLog(new Log(Action.HAND_DOWN, user));
            }
            saveLog(new Log(Action.SIGN_OUT, user));
        }

        user.setAuthorized(false);
        user.setRaisedHand(false);
        saveUser(user);
    }

    @Override
    public User raiseHand(Long userId) {
        User user = userRepository.getById(userId);
        Validator.validateAuthorizedUser(user);

        user.setRaisedHand(!user.isRaisedHand());
        saveUser(user);

        if (user.isRaisedHand()) {
            saveLog(new Log(Action.HAND_UP, user));
        } else {
            saveLog(new Log(Action.HAND_DOWN, user));
        }

        return user;
    }

    @Override
    public List<User> getAllStudents() {
        return userRepository.getAllByRole(Role.STUDENT);
    }

    @Override
    public List<User> getAuthorizedStudents() {
        return userRepository.getAllByRoleAndAuthorizedIsTrueOrderByIdAsc(Role.STUDENT);
    }

    @Override
    public List<Log> getAllStudentsLogs(Long studentId) {
        User student = userRepository.getById(studentId);

        return logRepository.getAllByUser(student);
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }

    private void saveLog(Log log) {
        logRepository.save(log);
    }
}
