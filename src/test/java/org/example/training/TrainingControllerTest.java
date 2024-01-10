package org.example.training;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.trainee.Trainee;
import org.example.trainee.TraineeController;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerController;
import org.example.trainer.TrainerService;
import org.example.training.dto.PostTrainingDTO;
import org.example.trainingType.TrainingTypeService;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainingControllerTest {

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

	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();

	@Test
	public void testPostTraining() throws Exception {
		String username = "testUsername";
		String password = "testPassword";

		User mockedUser = new User();
		mockedUser.setUsername(username);
		mockedUser.setPassword(password);

		Trainee mockedTrainee = new Trainee();
		Trainer mockedTrainer = new Trainer();

		PostTrainingDTO postTrainingDTO = new PostTrainingDTO();
		postTrainingDTO.setTrainerUsername(username);
		postTrainingDTO.setTraineeUsername(username);
		postTrainingDTO.setDuration(60);
		postTrainingDTO.setDate(LocalDateTime.now());
		postTrainingDTO.setName("TrainingName");

		when(userService.readByUserName(username)).thenReturn(mockedUser);
		when(traineeService.readByUsername(postTrainingDTO.getTraineeUsername())).thenReturn(mockedTrainee);
		when(trainerService.readByUsername(postTrainingDTO.getTrainerUsername())).thenReturn(mockedTrainer);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		String requestBody = objectMapper.writeValueAsString(postTrainingDTO);

		ResultActions result = mockMvc.perform(post("/api/training/post").param("username", username)
			.param("password", password)
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody));

		result.andExpect(status().isOk());

	}

}
