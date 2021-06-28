package br.com.rrmelo.concretedesafio.controller;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.*;
import br.com.rrmelo.concretedesafio.controller.form.UserForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.TokenOnly;
import br.com.rrmelo.concretedesafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> get(@PathVariable @NotNull UUID id,
                                    @RequestHeader(name = "token", required = false) UUID token) {
        if(token == null)
            throw new MissingTokenException();

        Optional<TokenOnly> optToken = userService.findToken(token);
        if (optToken.isPresent() && optToken.get().getToken().equals(token)) {
            Optional<User> optUser = userService.findById(id);
            if (optUser.isPresent()) {
                User user = optUser.get();
                long minutesDiff = user.getLastLogin().until(LocalDateTime.now(), ChronoUnit.MINUTES);
                if (minutesDiff < 30)
                    return ResponseEntity.ok(optUser.get());

                throw new InvalidSessionException();
            }

            throw new UserNotFoundException();
        }

        throw new InvalidTokenException();
    }

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
