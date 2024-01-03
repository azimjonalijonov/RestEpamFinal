package org.example.training;

import static org.junit.jupiter.api.Assertions.*;

import org.example.training.Training;
import org.example.training.TrainingDAO;
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

class TrainingDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@InjectMocks
	private TrainingDAO trainingDAO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.openSession()).thenReturn(session);
	}

	@Test
    void readAll() {
        when(session.createQuery(anyString(), eq(Training.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<Training> trainings = trainingDAO.readAll();

        assertNotNull(trainings);
        verify(session).createQuery(anyString(), eq(Training.class));
    }

	@Test
    void readById() {
        when(session.get(eq(Training.class), anyLong())).thenReturn(new Training());

        Training training = trainingDAO.readById(1L);

        assertNotNull(training);
        verify(session).get(eq(Training.class), anyLong());
    }

	@Test
	void createOrUpdate() {
		Training training = new Training();

		Training result = trainingDAO.createOrUpdate(training);

		assertNotNull(result);
		verify(session).saveOrUpdate(training);
	}

	@Test
	void deleteById() {
		Training training = new Training();
		when(session.get(eq(Training.class), anyLong())).thenReturn(training);

		boolean result = trainingDAO.deleteById(1L);

		assertTrue(result);
		verify(session).remove(training);
	}

	@Test
    void existById() {
        when(trainingDAO.readById(1l)).thenReturn(new Training());

        assertTrue(trainingDAO.existById(1L));
    }

}
