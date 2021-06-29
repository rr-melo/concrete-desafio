package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.AuthenticationException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.UsernameNotFoundException;
import br.com.rrmelo.concretedesafio.controller.form.LoginForm;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private UserRepository userRepository;
    private LoginService loginService;
    private TokenService tokenService;
    private final String secret = "m^gUb4PXXi2hGA4B5%xLpP4HZ$$2MdnE9SMWZfk!VAbhrkM^$cXFrL&SJuy$!2B@i8pT#&qBYN7F4VMe$Ktq!Ftb@XkS22zqcN@4QC@jzT!soa#h6uWhU9Q55ADXTc8B$hXunpWGVxSpr%t#LJ8@o8DA#Aq$%coTXnm9em7jvyH^LC*4X&wcEUG6xXNgWrK&xu5VnFMS8U*&7aMYNviawDG6@*u6Gp&3mW#G8y2@c46crjS9LFcBQ&GkT2kqYtVB";

    @BeforeEach
    void InitLoginServiceTest() {
        this.tokenService = new TokenService(secret);
        this.loginService = new LoginService(userRepository, this.tokenService);
    }

    @Test
    void ShouldThrowUsernameNotFoundExceptionWhenEmailIsNotPersisted() {
        LoginForm receivedLogin = createDummyLogin();

        doReturn(Optional.empty()).when(userRepository).findByEmail(receivedLogin.getEmail());
        Assertions.assertThrows(UsernameNotFoundException.class, () -> loginService.authenticate(receivedLogin));
    }

    @Test
    void ShouldThrowAuthenticationExceptionWhenPasswordIsDifferentThanPersisted() {
        LoginForm receivedLogin = createDummyLogin();
        User persistedUser = new User();
        persistedUser.setEmail("joao@silva.org");
        persistedUser.setPassword("hunter21");

        doReturn(Optional.of(persistedUser)).when(userRepository).findByEmail(receivedLogin.getEmail());
        Assertions.assertThrows(AuthenticationException.class, () -> loginService.authenticate(receivedLogin));
    }

    @Test
    void ShouldReturnUserWhenEmailIsPersistendAndPasswordIsEqualsToPersisted() {
        LoginForm receivedLogin = createDummyLogin();
        User persistedUser = new User();
        persistedUser.setEmail("joao@silva.org");
        persistedUser.setPassword("hunter2");

        doReturn(Optional.of(persistedUser)).when(userRepository).findByEmail(receivedLogin.getEmail());
        Assertions.assertEquals(persistedUser, loginService.authenticate(receivedLogin));
    }

    private LoginForm createDummyLogin() {
        return new LoginForm(
                "joao@silva.org",
                "hunter2"
        );
    }
}
