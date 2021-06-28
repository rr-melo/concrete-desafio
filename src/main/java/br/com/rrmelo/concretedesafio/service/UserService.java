package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.TokenOnly;
import br.com.rrmelo.concretedesafio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        String token = tokenService.generateToken(user);
        user.setToken(token);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<TokenOnly> findToken(String token) {
        return userRepository.findTokenByToken(token);
    }
}
