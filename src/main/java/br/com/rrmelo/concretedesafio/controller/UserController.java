package br.com.rrmelo.concretedesafio.controller;


import br.com.rrmelo.concretedesafio.controller.dto.UserDto;
import br.com.rrmelo.concretedesafio.controller.form.UserForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> get(@PathVariable @NotNull UUID id, @RequestHeader(name = "token", required = false) String token) {
        User user = userService.getUserProfile(token, id);
        UserDto userDto = new UserDto().from(user);
        return ResponseEntity.ok(userDto);
    }
  
    @PostMapping(value = "register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserForm userForm, UriComponentsBuilder uriBuilder) {
        User user = userService.save(userForm.toUser());
        UserDto userDto = new UserDto().from(user);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }
}
