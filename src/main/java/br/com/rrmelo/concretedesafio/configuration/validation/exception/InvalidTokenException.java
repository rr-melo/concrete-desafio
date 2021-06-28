package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Não autorizado");
    }
}
