package org.example.training;

import org.example.trainee.Trainee;
import org.example.trainer.Trainer;
import org.example.trainingType.TrainingType;
import org.example.util.validation.impl.TrainingErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceTest {

	private TrainingDAO trainingDAOMock;

	private TrainingErrorValidator trainingErrorValidatorMock;

	private TrainingService trainingService;

	@BeforeEach
	void setUp() {
		trainingDAOMock = mock(TrainingDAO.class);
		trainingErrorValidatorMock = mock(TrainingErrorValidator.class);
		trainingService = new TrainingService(trainingDAOMock, trainingErrorValidatorMock);
	}

	@Test
	void testReadAll() {
		List<Training> expectedTrainings = Arrays.asList(new Training(1L, null, null, null, null, null, null),
				new Training(2L, null, null, null, null, null, null));
		when(trainingDAOMock.readAll()).thenReturn(expectedTrainings);

		List<Training> result = trainingService.readAll();

		assertEquals(expectedTrainings, result);
	}

	@Test
	void testReadById_TrainingFound() {

		Long trainingId = 1L;
		Training expectedTraining = new Training(1L, null, null, null, null, null, null);
		when(trainingDAOMock.readById(trainingId)).thenReturn(expectedTraining);

		Training result = trainingService.readById(trainingId);

		assertEquals(expectedTraining, result);
	}

	@Test
	void testReadById_TrainingNotFound() {
		Long trainingId = 999L;
		when(trainingDAOMock.readById(trainingId)).thenReturn(null);

		assertNull(trainingService.readById(trainingId));
	}

	@Test
	void testCreate_ValidTraining() {
		Training createTraining = new Training(1l, new Trainee(), new Trainer(), "Nmadur", new TrainingType(),
				LocalDate.now().atStartOfDay(), 2);
		when(trainingErrorValidatorMock.isValidParamsForCreate(createTraining)).thenReturn(true);

		Training result = trainingService.create(createTraining);

		verify(trainingDAOMock, times(1)).createOrUpdate(createTraining);
	}

	@Test
	void testCreate_InvalidTraining() {
		Training createTraining = new Training(1L, null, null, null, null, null, null);
		;
		when(trainingErrorValidatorMock.isValidParamsForCreate(createTraining)).thenReturn(false);

		assertThrows(RuntimeException.class, () -> trainingService.create(createTraining));
		verify(trainingDAOMock, never()).createOrUpdate(createTraining);
	}

}
