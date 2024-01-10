package org.example.trainee;

import org.example.trainee.dto.PostTraineeDTO;

import org.example.trainee.dto.UpdateTraineeDTO;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.TrainerService;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TraineeControllerTest {

	private final TraineeService traineeService = Mockito.mock(TraineeService.class);

	private final UserService userService = Mockito.mock(UserService.class);

	private final TrainerService trainerService = Mockito.mock(TrainerService.class);

	private final TraineeTrainerService traineeTrainerService = Mockito.mock(TraineeTrainerService.class);

	private final TraineeController traineeController = new TraineeController(trainerService, userService,
			traineeService, traineeTrainerService);

	private final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();

	@Test
	public void testPostTrainee() throws Exception {
		PostTraineeDTO traineeDTO = new PostTraineeDTO();
		traineeDTO.setFirstname("Azimjon");
		traineeDTO.setLastname("Alijonov");
		traineeDTO.setAddress("123 Main St");
		// traineeDTO.setDob(LocalDate.now());
		User user = new User();
		user.setFirstName(traineeDTO.getFirstname());
		user.setLastName(traineeDTO.getLastname());
		Trainee trainee = new Trainee();
		trainee.setUser(user);
		trainee.setAddress(traineeDTO.getAddress());
		// trainee.setDateOfBirth(traineeDTO.getDob());
		when(userService.create(Mockito.any())).thenReturn(user);
		when(traineeService.create(Mockito.any())).thenReturn(trainee);
		//
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(traineeDTO);

		ResultActions result = mockMvc
			.perform(post("/api/trainee/post").contentType(MediaType.APPLICATION_JSON).content(requestBody));

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
		Trainee trainee = new Trainee();
		trainee.setId(1l);
		trainee.setUser(mockedUser);

		when(traineeService.readByUsername(username)).thenReturn(trainee);

		mockMvc
			.perform(MockMvcRequestBuilders.get("/api/trainee/get")
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

		UpdateTraineeDTO updateTraineeDTO = new UpdateTraineeDTO();
		updateTraineeDTO.setActive(true);
		updateTraineeDTO.setFirstname("UpdatedFirstName");
		updateTraineeDTO.setLastname("UpdatedLastName");
		updateTraineeDTO.setUsername("UpdatedUsername");
		// updateTraineeDTO.setDob("2022-01-10");
		updateTraineeDTO.setAddress("UpdatedAddress");

		Trainee mockedTrainee = new Trainee();
		mockedTrainee.setUser(user1);

		when(userService.readByUserName(Mockito.any())).thenReturn(user1);
		when(traineeService.readByUsername(Mockito.any())).thenReturn(mockedTrainee);

		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(updateTraineeDTO);

		ResultActions result = mockMvc.perform(put("/api/trainee/update").param("username", username)
			.param("password", password)
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestBody));

		result.andExpect(status().isOk());

	}

	@Test
	public void testDeleteTrainee() throws Exception {
		String username = "testUsername";
		String password = "testPassword";

		User mockedUser = new User();
		mockedUser.setUsername(username);
		mockedUser.setPassword(password);

		when(userService.readByUserName(username)).thenReturn(mockedUser);
		when(traineeService.deleteByUsername(username)).thenReturn(null);

		ResultActions result = mockMvc.perform(delete("/api/trainee/delete").param("username", username)
			.param("password", password)
			.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

}
