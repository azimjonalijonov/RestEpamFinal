package org.example.trainer;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.trainee.Trainee;
import org.example.trainee.TraineeController;
import org.example.trainee.TraineeService;
import org.example.trainee.dto.PostTraineeDTO;
import org.example.trainee.dto.UpdateTraineeDTO;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.dto.PostTrainerDTO;
import org.example.trainer.dto.UpdateTrainerDTO;
import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeService;
import org.example.trainingType.dto.TrainingTypeDTO;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainerControllerTest {

	private final TraineeService traineeService = Mockito.mock(TraineeService.class);

	private final UserService userService = Mockito.mock(UserService.class);

	private final TrainerService trainerService = Mockito.mock(TrainerService.class);

	private final TraineeTrainerService traineeTrainerService = Mockito.mock(TraineeTrainerService.class);

	private final TrainingTypeService trainingTypeService = Mockito.mock(TrainingTypeService.class);

	private final TrainerController trainerController = new TrainerController(traineeService, userService,
			trainerService, trainingTypeService);

	private final TraineeController traineeController = new TraineeController(trainerService, userService,
			traineeService, traineeTrainerService);

	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();

	@Test
	void testPost() throws Exception {
		TrainingTypeDTO trainingTypeDTO = new TrainingTypeDTO();
		trainingTypeDTO.setId(1l);
		PostTrainerDTO trainerDTO = new PostTrainerDTO();
		trainerDTO.setTrainingTypeDTO(trainingTypeDTO);
		trainerDTO.setFirstname("Azimjon");
		trainerDTO.setLastname("Alijonov");
		User user = new User();
		user.setFirstName(trainerDTO.getFirstname());
		user.setLastName(trainerDTO.getLastname());
		Trainer trainer = new Trainer();
		trainer.setUser(user);
		when(userService.create(Mockito.any())).thenReturn(user);
		when(trainerService.create(Mockito.any())).thenReturn(trainer);

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(trainerDTO);

		ResultActions result = mockMvc
			.perform(post("/api/trainer/post").contentType(MediaType.APPLICATION_JSON).content(requestBody));

		result.andExpect(status().isOk());
	}

	@Test
	public void testGetEndpoint() throws Exception {
		String username = "Azimjon.Alijonov";
		String password = "testPassword";
		User mockedUser = new User();
		mockedUser.setPassword(password);
		mockedUser.setUsername(username);
		when(userService.readByUserName(username)).thenReturn(mockedUser);
		Trainer trainer = new Trainer();
		trainer.setId(1l);
		trainer.setUser(mockedUser);

		when(trainerService.readByUsername(username)).thenReturn(trainer);

		mockMvc
			.perform(MockMvcRequestBuilders.get("/api/trainer/get")
				.param("username", username)
				.param("password", password)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void testUpdateTrainee() throws Exception {
		String username = "Azimjon.Alijonov";
		String password = "123";

		User user1 = new User();
		user1.setId(1l);
		user1.setUsername(username);
		user1.setPassword(password);

		UpdateTrainerDTO updateTraineeDTO = new UpdateTrainerDTO();
		updateTraineeDTO.setActive(true);
		updateTraineeDTO.setFirstname("UpdatedFirstName");
		updateTraineeDTO.setLastname("UpdatedLastName");
		updateTraineeDTO.setUsername("UpdatedUsername");

		Trainer mockedTrainer = new Trainer();
		mockedTrainer.setUser(user1);

		when(userService.readByUserName(Mockito.any())).thenReturn(user1);
		when(trainerService.readByUsername(Mockito.any())).thenReturn(mockedTrainer);

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(updateTraineeDTO);

		ResultActions result = mockMvc.perform(put("/api/trainer/update").param("username", username)
			.param("password", password)
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody));

		result.andExpect(status().isOk());

	}

	@Test
	public void testChangeStatus() throws Exception {
		String username = "testUsername";
		String password = "testPassword";
		Boolean bool = true;

		User mockedUser = new User();
		mockedUser.setUsername(username);
		mockedUser.setPassword(password);

		Trainer mockedTrainer = new Trainer();
		mockedTrainer.setId(1L);

		when(userService.readByUserName(username)).thenReturn(mockedUser);
		when(trainerService.readByUsername(username)).thenReturn(mockedTrainer);

		ResultActions result = mockMvc.perform(patch("/api/trainer/activateDeacivate").param("username", username)
			.param("password", password)
			.param("bool", bool.toString()) // Convert boolean to string
			.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

}
