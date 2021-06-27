package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class RegisteredEmailException extends RuntimeException {
    public RegisteredEmailException() {
        super("E-mail já existente");
    }
}
