package org.example.user;

import org.example.util.exception.UserNotFoundException;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.UserErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

	private UserDAO userDAOMock;

	private UserErrorValidator userErrorValidatorMock;

	private UserService userService;

	@BeforeEach
	void setUp() {
		userDAOMock = mock(UserDAO.class);
		userErrorValidatorMock = mock(UserErrorValidator.class);
		userService = new UserService(userDAOMock, userErrorValidatorMock);
	}

	@Test
	void testReadAll() {
		List<User> expectedUsers = Arrays.asList(new User(1L, "John Doe", "jonov", "user", "pass", false));
		when(userDAOMock.readAll()).thenReturn(expectedUsers);

		List<User> result = userService.readAll();

		assertEquals(expectedUsers, result);
	}

	@Test
	void testReadById_UserFound() {

		Long userId = 1L;
		User expectedUser = new User(1L, "John Doe", "jonov", "user", "pass", false);

		when(userDAOMock.readById(userId)).thenReturn(expectedUser);

		User result = userService.readById(userId);

		assertEquals(expectedUser, result);
	}

	@Test
	void testReadById_UserNotFound() {

		Long userId = 999L;
		when(userDAOMock.readById(userId)).thenReturn(null);

		assertThrows(NullPointerException.class, () -> userService.readById(userId));
	}

	@Test
	void testCreate_ValidUser() {
		User createUser = new User(1L, "John Doe", "jonov", "user", "pass", false);

		when(userErrorValidatorMock.isValidParamsForCreate(createUser)).thenReturn(true);

		User result = userService.create(createUser);

		assertEquals(createUser, result);
	}

	@Test
	void testCreate_InvalidUser() {
		User createUser = new User(1L, "John Doe", "jonov", "user", "pass", false);
		when(userErrorValidatorMock.isValidParamsForCreate(createUser)).thenReturn(false);

		assertThrows(ValidatorException.class, () -> userService.create(createUser));
		verify(userDAOMock, never()).createOrUpdate(createUser);
	}

}
