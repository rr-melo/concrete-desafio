package br.com.rrmelo.concretedesafio.service;

import br.com.rrmelo.concretedesafio.configuration.validation.exception.InvalidSessionException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.InvalidTokenException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.MissingTokenException;
import br.com.rrmelo.concretedesafio.configuration.validation.exception.UserNotFoundException;
import br.com.rrmelo.concretedesafio.controller.form.UserForm;
import br.com.rrmelo.concretedesafio.model.Phone;
import br.com.rrmelo.concretedesafio.model.Token;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.repository.TokenRepository;
import br.com.rrmelo.concretedesafio.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    private UserService userService;
    private TokenService tokenService;
    private final String secret = "m^gUb4PXXi2hGA4B5%xLpP4HZ$$2MdnE9SMWZfk!VAbhrkM^$cXFrL&SJuy$!2B@i8pT#&qBYN7F4VMe$Ktq!Ftb@XkS22zqcN@4QC@jzT!soa#h6uWhU9Q55ADXTc8B$hXunpWGVxSpr%t#LJ8@o8DA#Aq$%coTXnm9em7jvyH^LC*4X&wcEUG6xXNgWrK&xu5VnFMS8U*&7aMYNviawDG6@*u6Gp&3mW#G8y2@c46crjS9LFcBQ&GkT2kqYtVB";


    @BeforeEach
    void InitUserServiceTest() {
        this.tokenService = new TokenService(secret);
        this.userService = new UserService(userRepository, tokenRepository, tokenService);
    }

    @Test
    void ShouldHaveTokenWhenUserIsSaved() {
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User registeredUser = userService.save(createDummyUser());
        Assertions.assertNotNull(registeredUser.getToken());
    }

    @Test
    void ShouldThrowMissingTokenExceptionWhenTokenIsNull() {
        Assertions.assertThrows(MissingTokenException.class, () -> userService.getUserProfile(null, UUID.randomUUID()));
    }

    @Test
    void ShouldThrowInvalidTokenExceptionWhenTokenIsDifferentFromPersisted() {
        String persistedTokenValue = "JWT.Token1.Mocked";

        doReturn(Optional.empty()).when(tokenRepository).findByValue(persistedTokenValue);
        Assertions.assertThrows(InvalidTokenException.class, () -> userService.getUserProfile(persistedTokenValue, UUID.randomUUID()));
    }

    @Test
    void ShouldThrowUserNotFoundExceptionWhenTokenIsEqualsToPersistedAndUserIdIsNotFound() {
        UUID receivedId = UUID.randomUUID();
        User user = createDummyUser();
        user.setId(receivedId);

        String receivedTokenValue = "JWT.Token1.Mocked";
        Token persistedToken = new Token(user);
        persistedToken.setValue(receivedTokenValue);

        doReturn(Optional.of(persistedToken)).when(tokenRepository).findByValue(receivedTokenValue);
        doReturn(Optional.empty()).when(userRepository).findById(receivedId);
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserProfile(receivedTokenValue, receivedId));
    }

    @Test
    void ShouldThrowInvalidSessionExceptionWhenTokenIsEqualsToPersistedAndUserIdIsPersistedAndLastLoginWasAtLeast30MinutesAgo() {
        UUID receivedId = UUID.randomUUID();
        User user = createDummyUser();
        user.setId(receivedId);
        user.setLastLogin(LocalDateTime.now().minus(30, ChronoUnit.MINUTES));

        String receivedTokenValue = "JWT.Token1.Mocked";
        Token persistedToken = new Token(user);
        persistedToken.setValue(receivedTokenValue);

        doReturn(Optional.of(persistedToken)).when(tokenRepository).findByValue(receivedTokenValue);
        doReturn(Optional.of(user)).when(userRepository).findById(receivedId);
        Assertions.assertThrows(InvalidSessionException.class, () -> userService.getUserProfile(receivedTokenValue, receivedId));
    }

    @Test
    void ShouldReturnUserWhenTokenIsValidAndIdIsPersistedAndLastLoginWasNoLaterThan30MinutesAgo() {
        UUID receivedId = UUID.randomUUID();
        User user = createDummyUser();
        user.setId(receivedId);
        user.setLastLogin(LocalDateTime.now().minus(29, ChronoUnit.MINUTES));

        String receivedTokenValue = "JWT.Token1.Mocked";
        Token persistedToken = new Token(user);
        persistedToken.setValue(receivedTokenValue);

        doReturn(Optional.of(persistedToken)).when(tokenRepository).findByValue(receivedTokenValue);
        doReturn(Optional.of(user)).when(userRepository).findById(receivedId);
        Assertions.assertEquals(user, userService.getUserProfile(receivedTokenValue, receivedId));
    }

    private User createDummyUser() {
        HashSet<Phone> phones = new HashSet<>();
        phones.add(new Phone("987654321", "21"));
        UserForm userForm = new UserForm(
                "João da Silva",
                "joao@silva.org",
                "hunter2",
                phones
        );
        return userForm.toUser();
    }
}
