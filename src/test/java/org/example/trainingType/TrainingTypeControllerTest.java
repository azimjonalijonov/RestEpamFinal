package org.example.trainingType;

import static org.junit.jupiter.api.Assertions.*;

import org.example.trainee.TraineeController;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.TrainerController;
import org.example.trainer.TrainerService;
import org.example.training.TrainingController;
import org.example.training.TrainingService;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainingTypeControllerTest {

	private final TraineeService traineeService = Mockito.mock(TraineeService.class);

	private final UserService userService = Mockito.mock(UserService.class);

	private final TrainerService trainerService = Mockito.mock(TrainerService.class);

	private final TraineeTrainerService traineeTrainerService = Mockito.mock(TraineeTrainerService.class);

	private final TrainingTypeService trainingTypeService = Mockito.mock(TrainingTypeService.class);

	private final TrainerController trainerController = new TrainerController(traineeService, userService,
			trainerService, trainingTypeService);

	private final TrainingService trainingService = Mockito.mock(TrainingService.class);

	private final TraineeController traineeController = new TraineeController(trainerService, userService,
			traineeService, traineeTrainerService);

	private final TrainingController trainingController = new TrainingController(userService, traineeService,
			trainingService, trainerService, traineeTrainerService);

	private final TrainingTypeController trainingTypeController = new TrainingTypeController(trainingTypeService);

	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(trainingTypeController).build();

	@Test
	public void testGetTrainingTypes() throws Exception {
		List<TrainingType> mockedTrainingTypes = Arrays.asList(new TrainingType(1l, "sdfg"),
				new TrainingType(2l, "qwe"));

		when(trainingTypeService.readAll()).thenReturn(mockedTrainingTypes);

		ResultActions result = mockMvc.perform(get("/trainingType/get").contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

}
