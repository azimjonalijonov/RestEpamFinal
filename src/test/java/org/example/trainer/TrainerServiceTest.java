package org.example.trainer;

import org.example.trainingType.TrainingType;
import org.example.user.User;
import org.example.util.validation.impl.TrainerErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

	private TrainerDAO trainerDAOMock;

	private TrainerErrorValidator trainerErrorValidatorMock;

	private TrainerService trainerService;

	@BeforeEach
	void setUp() {
		trainerDAOMock = mock(TrainerDAO.class);
		trainerErrorValidatorMock = mock(TrainerErrorValidator.class);
		trainerService = new TrainerService(trainerDAOMock, trainerErrorValidatorMock);
	}

	@Test
	void testReadById_TrainerFound() {
		Long trainerId = 1L;
		Trainer expectedTrainer = new Trainer(trainerId, null, null);
		when(trainerDAOMock.readById(trainerId)).thenReturn(expectedTrainer);

		Trainer result = trainerService.readById(trainerId);

		assertEquals(expectedTrainer, result);
	}

	@Test
	void testReadById_TrainerNotFound() {
		Long trainerId = 999L;
		when(trainerDAOMock.readById(trainerId)).thenReturn(null);

		assertNull(trainerService.readById(trainerId));
	}

	@Test
	void testCreate_ValidTrainer() {
		User user = new User();

		Trainer createTrainer = new Trainer(1L, new TrainingType(), new User());
		when(trainerErrorValidatorMock.isValidParamsForCreate(createTrainer)).thenReturn(true);

		verify(trainerDAOMock, times(0)).createOrUpdate(createTrainer);
	}

	@Test
	void testCreate_InvalidTrainer() {
		Trainer createTrainer = new Trainer(1L, null, null);
		when(trainerErrorValidatorMock.isValidParamsForCreate(createTrainer)).thenReturn(false);

		assertThrows(RuntimeException.class, () -> trainerService.create(createTrainer));
		verify(trainerDAOMock, never()).createOrUpdate(createTrainer);
	}

	@Test
	void testReadByUsername() {
		String username = "Azimjon.Alijonov";
		when(trainerDAOMock.readByUsername(username)).thenReturn(new Trainer(1L, null, null));

		Trainer result = trainerService.readByUsername(username);

		assertNotNull(result);
	}

	//
	@Test
	void testPasswordChangeTrainer() {
		String newPassword = "newPassword";
		Long trainerId = 1L;
		when(trainerDAOMock.updatePassword(newPassword, trainerId)).thenReturn("Password updated successfully");

		String result = trainerService.passwordChangeTrainer(newPassword, trainerId);

		assertEquals("Password updated successfully", result);
	}

	@Test
	void testChangeActivation() {
		Boolean newActivationStatus = true;
		Long trainerId = 1L;
		when(trainerDAOMock.changeActivation(newActivationStatus, trainerId))
			.thenReturn("Activation status changed successfully");

		String result = trainerService.changeActivation(newActivationStatus, trainerId);

		assertEquals("Activation status changed successfully", result);
	}

	@Test
	void testGetSpecificTrainers() {
		List<Trainer> expectedTrainers = Arrays.asList(new Trainer(1L, null, null), new Trainer(2L, null, null));
		when(trainerDAOMock.findActiveTrainersWithNoAssignees()).thenReturn(expectedTrainers);

		List<Trainer> result = trainerService.getSpecificTrainers();

		assertEquals(expectedTrainers, result);
	}

}
