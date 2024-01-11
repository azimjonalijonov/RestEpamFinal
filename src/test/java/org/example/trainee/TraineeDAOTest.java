package org.example.trainee;

import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private TraineeDAO traineeDAO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.openSession()).thenReturn(session);
	}

	@Test
    void readAll() {
         when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<Trainee> trainees = traineeDAO.readAll();

        assertNotNull(trainees);
        verify(session).createQuery(anyString(), eq(Trainee.class));
    }

	@Test
    void readById() {
        when(session.get(eq(Trainee.class), anyLong())).thenReturn(new Trainee());

        Trainee trainee = traineeDAO.readById(1L);

        assertNotNull(trainee);
        verify(session).get(eq(Trainee.class), anyLong());
    }

	@Test
	void createOrUpdate() {
		Trainee trainee = new Trainee();

		Trainee result = traineeDAO.createOrUpdate(trainee);

		assertNotNull(result);
		verify(session).saveOrUpdate(trainee);
	}

	@Test
	void deleteById() {
		Trainee trainee = new Trainee();
		when(session.get(eq(Trainee.class), anyLong())).thenReturn(trainee);
		boolean result = traineeDAO.deleteById(1L);
		assertTrue(result);
		verify(session).remove(trainee);
	}

	@Test
	void updatePassword() {
		Trainee trainee = new Trainee();
		trainee.setUser(new User());
		traineeDAO.setUserDAO(userDAO);

		when(session.get(eq(Trainee.class), anyLong())).thenReturn(trainee);

		String result = traineeDAO.updatePassword("newPassword", 1L);

		assertNotNull(result);
		verify(userDAO).createOrUpdateForPassword(trainee.getUser());
	}

	@Test
	void deleteByUsername() {
		User user = new User();
		user.setId(1L);
		user.setUsername("Azimjon.Alijonov");
		user.setLastName("Alijonov");
		user.setFirstName("Azimjon");
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		trainee.setId(1L);
		when(userDAO.readByUsername(anyString())).thenReturn(user);
		traineeDAO.setUserDAO(userDAO);
		SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		Session session = Mockito.mock(Session.class);
		traineeDAO.setSession(session);

		TraineeDAO traineeDAO = new TraineeDAO(sessionFactory);

		Query<Trainee> query = Mockito.mock(Query.class);
		String result = traineeDAO.deleteByUsername("Azimjon.Alijonov");
		assertNotNull(result);

	}

}
