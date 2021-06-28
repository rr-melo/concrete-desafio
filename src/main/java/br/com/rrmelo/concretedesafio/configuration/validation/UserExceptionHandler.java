package br.com.rrmelo.concretedesafio.configuration.validation;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(RegisteredEmailException.class)
    public ExceptionDto handle(RegisteredEmailException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingTokenException.class)
    public ExceptionDto handle(MissingTokenException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidTokenException.class)
    public ExceptionDto handle(InvalidTokenException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto handle(UserNotFoundException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidSessionException.class)
    public ExceptionDto handle(InvalidSessionException e) {
        return new ExceptionDto(e.getMessage());
    }
}
