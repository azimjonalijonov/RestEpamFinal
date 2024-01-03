package org.example.trainingType;

import static org.junit.jupiter.api.Assertions.*;

import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeDAO;
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

class TrainingTypeDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@InjectMocks
	private TrainingTypeDAO trainingTypeDAO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		when(sessionFactory.openSession()).thenReturn(session);
	}

	@Test
    void readAll() {
        when(session.createQuery(anyString(), eq(TrainingType.class))).thenReturn(mock(org.hibernate.query.Query.class));

        List<TrainingType> trainingTypes = trainingTypeDAO.readAll();

        assertNotNull(trainingTypes);
        verify(session).createQuery(anyString(), eq(TrainingType.class));
    }

	@Test
    void readById() {
        when(session.get(eq(TrainingType.class), anyLong())).thenReturn(new TrainingType());

        TrainingType trainingType = trainingTypeDAO.readById(1L);

        assertNotNull(trainingType);
        verify(session).get(eq(TrainingType.class), anyLong());
    }

	@Test
	void createOrUpdate() {
		TrainingType trainingType = new TrainingType();

		TrainingType result = trainingTypeDAO.createOrUpdate(trainingType);

		assertNotNull(result);
		verify(session).saveOrUpdate(trainingType);
	}

	@Test
    void existById() {
        when(trainingTypeDAO.readById(1l)).thenReturn(new TrainingType());

        assertTrue(trainingTypeDAO.existById(1L));
    }

}
