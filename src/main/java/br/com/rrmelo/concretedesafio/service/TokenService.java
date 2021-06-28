package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    @Value("${concrete.desafio.jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Date today = new Date();
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withIssuer("Concrete - Desafio Java")
                .withSubject(user.getEmail())
                .withIssuedAt(today)
                .sign(algorithm);

        return token;
    }
}
