package org.example.trainingType;

import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.TrainingTypeErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingTypeServiceTest {

	private TrainingTypeDAO trainingTypeDAOMock;

	private TrainingTypeErrorValidator trainingTypeErrorValidatorMock;

	private TrainingTypeService trainingTypeService;

	@BeforeEach
	void setUp() {
		trainingTypeDAOMock = mock(TrainingTypeDAO.class);
		trainingTypeErrorValidatorMock = mock(TrainingTypeErrorValidator.class);
		trainingTypeService = new TrainingTypeService(trainingTypeDAOMock, trainingTypeErrorValidatorMock);
	}

	@Test
	void testReadAll() {
		List<TrainingType> expectedTrainingTypes = Arrays.asList(new TrainingType(1L, "Cardio"),
				new TrainingType(2L, "Strength"));
		when(trainingTypeDAOMock.readAll()).thenReturn(expectedTrainingTypes);

		List<TrainingType> result = trainingTypeService.readAll();

		assertEquals(expectedTrainingTypes, result);
	}

	@Test
	void testReadById_TrainingTypeFound() {
		Long trainingTypeId = 1L;
		TrainingType expectedTrainingType = new TrainingType(trainingTypeId, "Cardio");
		when(trainingTypeDAOMock.readById(trainingTypeId)).thenReturn(expectedTrainingType);

		TrainingType result = trainingTypeService.readById(trainingTypeId);

		assertEquals(expectedTrainingType, result);
	}

	@Test
	void testReadById_TrainingTypeNotFound() {
		Long trainingTypeId = 999L;
		when(trainingTypeDAOMock.readById(trainingTypeId)).thenReturn(null);

		assertNull(trainingTypeService.readById(trainingTypeId));
	}

	@Test
	void testAdd_ValidTrainingType() {
		TrainingType trainingType = new TrainingType(1L, "Cardio");
		when(trainingTypeErrorValidatorMock.isValidParamsForCreate(trainingType)).thenReturn(true);

		TrainingType result = trainingTypeService.add(trainingType);

		verify(trainingTypeDAOMock, times(1)).createOrUpdate(trainingType);
	}

	@Test
	void testAdd_InvalidTrainingType() {
		TrainingType trainingType = new TrainingType(1L, "Cardio");
		when(trainingTypeErrorValidatorMock.isValidParamsForCreate(trainingType)).thenReturn(false);

		assertThrows(ValidatorException.class, () -> trainingTypeService.add(trainingType));
		verify(trainingTypeDAOMock, never()).createOrUpdate(trainingType);
	}

}
