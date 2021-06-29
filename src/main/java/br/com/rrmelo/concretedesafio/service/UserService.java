package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.*;
import br.com.rrmelo.concretedesafio.model.Token;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.TokenRepository;
import br.com.rrmelo.concretedesafio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
    }

    public User save(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (!optionalUser.isPresent()) {
            Token token = tokenService.generateToken(user.getToken());
            user.setToken(token);
            return userRepository.save(user);
        }

        throw new RegisteredEmailException();
    }

    public User getUserProfile(String token, UUID id) {
        return verifyToken(token, id);
    }

    private User verifyToken(String token, UUID id) {
        if(token == null)
            throw new MissingTokenException();

        Optional<Token> optToken = tokenRepository.findByValue(token);
        if (optToken.isPresent() && optToken.get().getUser().getId().equals(id)) return verifyId(id);

        throw new InvalidTokenException();
    }

    private User verifyId(UUID id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) return verifySessionTime(optUser.get());

        throw new UserNotFoundException();
    }

    private User verifySessionTime(User user) {
        long minutesDiff = user.getLastLogin().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (minutesDiff < 30)
            return user;

        throw new InvalidSessionException();
    }
}
