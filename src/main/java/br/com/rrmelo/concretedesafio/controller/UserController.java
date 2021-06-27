package br.com.rrmelo.concretedesafio.controller;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.RegisteredEmailException;
import br.com.rrmelo.concretedesafio.controller.form.UserForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            value = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Transactional
    public ResponseEntity<User> register(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder)
            throws RegisteredEmailException {
        Optional<User> optionalUser = userService.findByEmail(userForm.getEmail());
        if (!optionalUser.isPresent()) {
            User user = userService.save(userForm.toUser());
            URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(uri).body(user);
        }

        throw new RegisteredEmailException();
    }
}
