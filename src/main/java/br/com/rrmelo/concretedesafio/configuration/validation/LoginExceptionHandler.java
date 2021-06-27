package br.com.rrmelo.concretedesafio.configuration.validation;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.AuthenticationException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class LoginExceptionHandler {

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ExceptionDto handle(AuthenticationException e) {
        return new ExceptionDto(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ExceptionDto handle(UsernameNotFoundException e) {
        return new ExceptionDto(e.getMessage());
    }
}
