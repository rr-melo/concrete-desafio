package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
