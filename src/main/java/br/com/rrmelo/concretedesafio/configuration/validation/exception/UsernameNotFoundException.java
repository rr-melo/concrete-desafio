package br.com.rrmelo.concretedesafio.configuration.validation.exception;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException() {
        super("Usuário e/ou senha inválidos");
    }
}
