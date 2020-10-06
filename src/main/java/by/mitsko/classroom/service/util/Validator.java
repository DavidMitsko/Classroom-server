package by.mitsko.classroom.service.util;

import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.exception.InvalidDataException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z\\d]{3,20}$");

    public static void validateAuthorizedUser(User user) {
        if(user == null) {
            throw new InvalidDataException("No such user exists");
        }
        if(!user.isAuthorized()) {
            throw new AccessDeniedException("This user isn't authorized");
        }
    }

    public static void validateUsername(String username) {
        if(username == null || username.equals("")) {
            throw new InvalidDataException("Empty name");
        }
        Matcher matcher = usernamePattern.matcher(username);
        if(!matcher.matches()) {
            throw new InvalidDataException("Invalid name");
        }
    }
}
