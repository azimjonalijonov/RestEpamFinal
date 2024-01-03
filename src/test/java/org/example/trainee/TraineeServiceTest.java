package org.example.trainee;

import org.example.training.Training;
import org.example.user.User;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.TraineeErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeServiceTest {

	private TraineeDAO traineeDAOMock;

	private TraineeErrorValidator traineeErrorValidatorMock;

	private TraineeService traineeService;

	@BeforeEach
	void setUp() {
		traineeDAOMock = mock(TraineeDAO.class);
		traineeErrorValidatorMock = mock(TraineeErrorValidator.class);
		traineeService = new TraineeService(traineeDAOMock, traineeErrorValidatorMock);
	}

	@Test
	void testReadAll() {
		List<Trainee> expectedTrainees = Arrays.asList(new Trainee(1l, null, null, null),
				new Trainee(2l, null, null, null));
		when(traineeDAOMock.readAll()).thenReturn(expectedTrainees);

		List<Trainee> result = traineeService.readAll();

		assertEquals(expectedTrainees, result);
	}

	@Test
	void testReadById_TraineeFound() {
		Long traineeId = 1L;
		Trainee expectedTrainee = new Trainee(2l, null, null, null);
		when(traineeDAOMock.readById(traineeId)).thenReturn(expectedTrainee);
		Trainee result = traineeService.readById(traineeId);

		assertEquals(expectedTrainee, result);
	}

	@Test
	void testReadById_TraineeNotFound() {
		Long traineeId = 999L;
		when(traineeDAOMock.readById(traineeId)).thenReturn(null);
		assertNull(traineeService.readById(traineeId));
	}

	//
	@Test
	void testCreate_ValidTrainee() {
		Trainee createTrainee = new Trainee(1L, LocalDate.now(), "somewhere", new User());
		when(traineeErrorValidatorMock.isValidParamsForCreate(createTrainee)).thenReturn(true);

		Trainee result = traineeService.create(createTrainee);

		assertEquals(createTrainee, result);
		verify(traineeDAOMock, times(1)).createOrUpdate(createTrainee);
	}

	@Test
	void testCreate_InvalidTrainee() {
		Trainee createTrainee = new Trainee(1L, LocalDate.now(), null, new User());
		when(traineeErrorValidatorMock.isValidParamsForCreate(createTrainee)).thenReturn(false);

		assertThrows(ValidatorException.class, () -> traineeService.create(createTrainee));
		verify(traineeDAOMock, never()).createOrUpdate(createTrainee);
	}

	@Test
	void testReadByUsername() {
		String username = "Azimjon";
		when(traineeDAOMock.readByUsername(username)).thenReturn(new Trainee(1L, null, null, null));

		Trainee result = traineeService.readByUsername(username);

		assertNotNull(result);
	}

	@Test
	void testPasswordChangeTrainee() {
		String newPassword = "newPassword";
		Long traineeId = 1L;
		when(traineeDAOMock.updatePassword(newPassword, traineeId)).thenReturn("Password updated successfully");

		String result = traineeService.passwordChangeTrainee(newPassword, traineeId);

		assertEquals("Password updated successfully", result);
	}

	@Test
	void testChangeActivation() {
		Boolean newActivationStatus = true;
		Long traineeId = 1L;
		when(traineeDAOMock.changeActivation(newActivationStatus, traineeId))
			.thenReturn("Activation status changed successfully");

		String result = traineeService.changeActivation(newActivationStatus, traineeId);

		assertEquals("Activation status changed successfully", result);
	}

	@Test
	void testDeleteByUsername() {
		String username = "Azimjon";
		when(traineeDAOMock.deleteByUsername(username)).thenReturn("Trainee deleted successfully");

		String result = traineeService.deleteByUsername(username);

		assertEquals("Trainee deleted successfully", result);
	}

	@Test
	void testGetTraineeTrainingList() {
		String username = "john_doe";
		Trainee trainee = new Trainee(1L, LocalDate.now(), "somewhere", new User());
		List<Training> expectedTrainings = Arrays.asList(new Training(1L, null, null, null, null, null, null),
				new Training(2L, null, null, null, null, null, null));
		trainee.setTrainings(expectedTrainings);
		when(traineeDAOMock.readByUsername(username)).thenReturn(trainee);

		List<Training> result = traineeService.getTraineeTrainingList(username, null);

		assertEquals(expectedTrainings, result);
	}

}
