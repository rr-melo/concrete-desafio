package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class MissingTokenException extends RuntimeException {
    public MissingTokenException() {
        super("Não autorizado");
    }
}
