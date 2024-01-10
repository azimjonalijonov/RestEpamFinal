package org.example.user;

import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	UserService userService;

	@InjectMocks
	private UserDAO userDAO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.openSession()).thenReturn(session);
	}

	@Test
    void readAll() {
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<User> users = userDAO.readAll();

        assertNotNull(users);
        verify(session).createQuery(anyString(), eq(User.class));
    }

	@Test
    void readById() {
        when(session.get(eq(User.class), eq(1L))).thenReturn(new User());

        User user = userDAO.readById(1L);

        assertNotNull(user);
        verify(session).get(eq(User.class), eq(1L));
    }

	@Test
	void testCreateUser() {
		User user = new User();
		user.setFirstName("John");
		user.setLastName("Doe");

		Session session = Mockito.mock(Session.class);
		Transaction transaction = Mockito.mock(Transaction.class);

		when(sessionFactory.openSession()).thenReturn(session);
		when(session.beginTransaction()).thenReturn(transaction);

		Mockito.doAnswer(invocation -> {
			User savedUser = invocation.getArgument(0);
			assertNotNull(savedUser.getUsername());
			assertNotNull(savedUser.getPassword());
			return null;
		}).when(session).save(Mockito.any(User.class));

	}

	@Test
	void update() {
		User user = new User();

		User result = userDAO.update(user);

		assertNotNull(result);
		verify(session).update(user);
	}

	@Test
    void deleteById() {
        when(session.get(eq(User.class), eq(1L))).thenReturn(new User());

        assertTrue(userDAO.deleteById(1L));
        verify(session).remove(any());
    }

}
