package org.example.user;

import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

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
	void create() {
		User user = new User();

		User result = userDAO.create(user);

		assertNotNull(result);
		assertNotNull(result.getUsername());
		assertNotNull(result.getPassword());
		verify(session).saveOrUpdate(user);
	}

	@Test
	void update() {
		User user = new User();

		User result = userDAO.update(user);

		assertNotNull(result);
		verify(session).update(user);
	}

	@Test
	void createOrUpdateForPassword() {
		User user = new User();
		user.setId(1L);

		when(session.beginTransaction()).thenReturn(mock(org.hibernate.Transaction.class));

		User result = userDAO.createOrUpdateForPassword(user);

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
