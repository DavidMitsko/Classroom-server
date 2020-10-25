package by.mitsko.classroom.service.impl;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Role;
import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.repository.UserRepository;
import by.mitsko.classroom.service.LogService;
import by.mitsko.classroom.service.UserService;
import by.mitsko.classroom.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LogService logService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LogService logService) {
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @Override
    public User signIn(String username, Role role, String email) {
        Validator.validateUsername(username);

        User user = userRepository.getByUsername(username);
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
            logService.add(Action.SIGN_IN, user);
        }

        return user;
    }

    @Override
    public void signOut(Long userId) {
        User user = userRepository.getById(userId);
        Validator.validateAuthorizedUser(user);

        if (user.getRole() == Role.STUDENT) {
            if(user.isRaisedHand()) {
                logService.add(Action.HAND_DOWN, user);
            }
            logService.add(Action.SIGN_OUT, user);
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
            logService.add(Action.HAND_UP, user);
        } else {
            logService.add(Action.HAND_DOWN, user);
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
    public void changeEmail(Long userId, String email) {
        User user = userRepository.getById(userId);
        user.setEmail(email);

        saveUser(user);
    }

    private void saveUser(User user) {
        userRepository.save(user);
    }
}
