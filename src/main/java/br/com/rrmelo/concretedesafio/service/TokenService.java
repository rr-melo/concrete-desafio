package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.model.Token;
import br.com.rrmelo.concretedesafio.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private String secret;

    public TokenService(@Value("${concrete.desafio.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public Token generateToken(Token token) {
        Date today = new Date();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String tokenValue = JWT.create()
                .withIssuer("Concrete - Desafio Java")
                .withSubject(token.getUser().getEmail())
                .withIssuedAt(today)
                .sign(algorithm);
        token.setValue(tokenValue);
        return token;
    }
}
