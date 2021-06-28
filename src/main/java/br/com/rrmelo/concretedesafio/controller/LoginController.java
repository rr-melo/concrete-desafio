package br.com.rrmelo.concretedesafio.controller;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.AuthenticationException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.UsernameNotFoundException;
import br.com.rrmelo.concretedesafio.controller.form.LoginForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.service.UserService;
import br.com.rrmelo.concretedesafio.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class LoginController {

    private final UserService userService;
    private final TokenService tokenService;

    public LoginController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(
            value = "login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public ResponseEntity<User> authentication(@RequestBody @Valid LoginForm loginForm) {
        Optional<User> optionalUser = userService.findByEmail(loginForm.getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getPassword().equals(loginForm.getPassword())) {
                user.setToken(tokenService.generateToken(user));
                user.setLastLogin(LocalDateTime.now());
                user.setModified(LocalDateTime.now());
                return ResponseEntity.ok(user);
            }

            throw new AuthenticationException();
        }
        throw new UsernameNotFoundException();
    }
}
