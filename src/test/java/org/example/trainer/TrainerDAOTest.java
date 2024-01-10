package org.example.trainer;

import org.example.trainer.Trainer;
import org.example.trainer.TrainerDAO;
import org.example.user.User;
import org.example.user.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private UserDAO userDAO;

	@InjectMocks
	private TrainerDAO trainerDAO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.openSession()).thenReturn(session);
	}

	@Test
    void readAll() {
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<Trainer> trainers = trainerDAO.readAll();

        assertNotNull(trainers);
        verify(session).createQuery(anyString(), eq(Trainer.class));
    }

	@Test
    void readById() {
        when(session.get(eq(Trainer.class), anyLong())).thenReturn(new Trainer());

        Trainer trainer = trainerDAO.readById(1L);

        assertNotNull(trainer);
        verify(session).get(eq(Trainer.class), anyLong());
    }

	@Test
	void createOrUpdate() {
		Trainer trainer = new Trainer();

		Trainer result = trainerDAO.createOrUpdate(trainer);

		assertNotNull(result);
		verify(session).saveOrUpdate(trainer);
	}

	@Test
	void deleteById() {
		Trainer trainer = new Trainer();
		when(session.get(eq(Trainer.class), anyLong())).thenReturn(trainer);

		boolean result = trainerDAO.deleteById(1L);

		assertTrue(result);
		verify(session).remove(trainer);
	}

	@Test
	void readByUsername() {
		Trainer tr = new Trainer();
		User user = new User();
		user.setFirstName("Azimjon");
		user.setLastName("Alijonov");
		userDAO.create(user);
		Trainer t = new Trainer();
		t.setUser(user);
		trainerDAO.createOrUpdate(t);
		when(session.get(eq(Trainer.class), anyLong())).thenReturn(tr);
		assertNotNull(tr);
	}

	@Test
	void updatePassword() {
		Trainer trainer = new Trainer();
		trainer.setUser(new User());
		when(session.get(eq(Trainer.class), anyLong())).thenReturn(trainer);

		String result = trainerDAO.updatePassword("1234", 1L);

		assertNotNull(result);
		verify(userDAO).createOrUpdateForPassword(trainer.getUser());
	}

	@Test
	void changeActivation() {
		Trainer trainer = new Trainer();
		trainer.setUser(new User());
		when(session.get(eq(Trainer.class), anyLong())).thenReturn(trainer);

		String result = trainerDAO.changeActivation(true, 1L);

		assertNotNull(result);
		verify(userDAO).update(trainer.getUser());
	}

	@Test
    void findActiveTrainersWithNoAssignees() {
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<Trainer> trainers = trainerDAO.findActiveTrainersWithNoAssignees();

        assertNotNull(trainers);
        verify(session).createQuery(anyString(), eq(Trainer.class));
    }

}
