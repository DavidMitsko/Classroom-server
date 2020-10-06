package by.mitsko.classroom.exception;

public class UserAuthorizedException extends RuntimeException {
    public UserAuthorizedException() {
    }

    public UserAuthorizedException(String message) {
        super(message);
    }

    public UserAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthorizedException(Throwable cause) {
        super(cause);
    }
}
