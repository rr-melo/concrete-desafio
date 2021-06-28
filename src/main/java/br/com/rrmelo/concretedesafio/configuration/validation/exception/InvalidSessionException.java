package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
        super("Sessão inválida");
    }
}
