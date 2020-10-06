package by.mitsko.classroom.service.impl;

import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.repository.UserRepository;
import by.mitsko.classroom.service.UserService;
import by.mitsko.classroom.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signIn(String username) {
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
            user = new User(username);
        }
        save(user);
        return user;
    }

    @Override
    public void signOut(Long userId) {
        User user = userRepository.getById(userId);
        Validator.validateAuthorizedUser(user);

        user.setAuthorized(false);
        user.setRaisedHand(false);
        save(user);
    }

    @Override
    public User raiseHand(Long userId) {
        User user = userRepository.getById(userId);
        Validator.validateAuthorizedUser(user);

        user.setRaisedHand(!user.isRaisedHand());
        save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllByAuthorizedIsTrueOrderByIdAsc();
    }

    private void save(User user) {
        userRepository.save(user);
    }
}
