package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.AuthenticationException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.UsernameNotFoundException;
import br.com.rrmelo.concretedesafio.controller.form.LoginForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public LoginService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User authenticate(LoginForm loginForm) {
        return verifyEmail(loginForm);
    }

    private User verifyEmail(LoginForm loginForm) {
        Optional<User> optionalUser = userRepository.findByEmail(loginForm.getEmail());
        if(optionalUser.isPresent()) return verifyPassword(loginForm.getPassword(), optionalUser.get());

        throw new UsernameNotFoundException();
    }

    private User verifyPassword(String password, User user) {
        if(user.getPassword().equals(password)) {
            user.setToken(tokenService.generateToken(user.getToken()));
            user.setLastLogin(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
            return user;
        }

        throw new AuthenticationException();
    }
}
