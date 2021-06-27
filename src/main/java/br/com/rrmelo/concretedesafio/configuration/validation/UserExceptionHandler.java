package br.com.rrmelo.concretedesafio.configuration.validation;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.RegisteredEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(RegisteredEmailException.class)
    public ExceptionDto handle(RegisteredEmailException e) {
        ExceptionDto error = new ExceptionDto(e.getMessage());
        return error;
    }
}
