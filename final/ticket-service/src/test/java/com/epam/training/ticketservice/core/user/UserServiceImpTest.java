package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepository);

    @Test
    void testLoginShouldSetLoggedInUserWhenUsernameAndPasswordAreCorrect() {
        // Given
        User user = new User("user", "password", User.Role.USER);
        Optional<User> expected = Optional.of(user);
        when(userRepository.findByUsernameAndPassword("user", "pass")).thenReturn(Optional.of(user));

        // When
        Optional<UserDto> actual = underTest.login("user", "pass");

        // Then
        assertEquals(expected.get().getUsername(), actual.get().getUsername());
        assertEquals(expected.get().getRole(), actual.get().getRole());
        verify(userRepository).findByUsernameAndPassword("user", "pass");
    }

    @Test
    void testLoginShouldReturnOptionalEmptyWhenUsernameOrPasswordAreNotCorrect() {
        // Given
        Optional<UserDto> expected = Optional.empty();
        when(userRepository.findByUsernameAndPassword("dummy", "dummy")).thenReturn(Optional.empty());

        // When
        Optional<UserDto> actual = underTest.login("dummy", "dummy");

        // Then
        assertEquals(expected, actual);
        verify(userRepository).findByUsernameAndPassword("dummy", "dummy");
    }

    @Test
    void testLogoutShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.logout();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testLogoutShouldReturnThePreviouslyLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        User user = new User("user", "password", User.Role.USER);
        when(userRepository.findByUsernameAndPassword("user", "pass")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("user", "password");

        // When
        Optional<UserDto> actual = underTest.logout();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        // Given
        User user = new User("user", "password", User.Role.USER);
        when(userRepository.findByUsernameAndPassword("user", "pass")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("user", "password");

        // When
        Optional<UserDto> actual = underTest.describe();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        // Given
        Optional<UserDto> expected = Optional.empty();

        // When
        Optional<UserDto> actual = underTest.describe();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testRegisterUserShouldCallUserRepositoryWhenTheInputIsValid() {
        // Given
        // When
        underTest.registerUser("user", "pass");

        // Then
        verify(userRepository).save(new User("user", "pass", User.Role.USER));
    }
}
