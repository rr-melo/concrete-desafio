package br.com.rrmelo.concretedesafio.repository;

import br.com.rrmelo.concretedesafio.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByValue(String value);
}
