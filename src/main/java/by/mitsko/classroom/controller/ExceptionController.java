package by.mitsko.classroom.controller;

import by.mitsko.classroom.dto.response.ErrorDTO;
import by.mitsko.classroom.exception.AccessDeniedException;
import by.mitsko.classroom.exception.InvalidDataException;
import by.mitsko.classroom.exception.UserAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAuthorizedException.class)
    public ResponseEntity<ErrorDTO> handle(final UserAuthorizedException exception) {
        ErrorDTO message = new ErrorDTO(exception);
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorDTO> handle(final AccessDeniedException exception) {
        ErrorDTO message = new ErrorDTO(exception);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidDataException.class)
    public ResponseEntity<ErrorDTO> handle(final InvalidDataException exception) {
        ErrorDTO message = new ErrorDTO(exception);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
