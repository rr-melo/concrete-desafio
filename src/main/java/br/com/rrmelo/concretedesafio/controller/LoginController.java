package br.com.rrmelo.concretedesafio.controller;

import br.com.rrmelo.concretedesafio.controller.dto.UserDto;
import br.com.rrmelo.concretedesafio.controller.form.LoginForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.service.LoginService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UserDto> authentication(@RequestBody @Valid LoginForm loginForm) {
        User user = loginService.authenticate(loginForm);
        UserDto userDto = new UserDto().from(user);
        return ResponseEntity.ok(userDto);
    }
}
