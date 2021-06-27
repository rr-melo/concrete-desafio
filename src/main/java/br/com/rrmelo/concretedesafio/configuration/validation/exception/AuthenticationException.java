package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Usuário e/ou senha inválidos");
    }
}
