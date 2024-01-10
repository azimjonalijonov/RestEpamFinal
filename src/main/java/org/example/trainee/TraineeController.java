package org.example.trainee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.trainee.dto.PostTraineeDTO;
import org.example.trainee.dto.UpdateTraineeDTO;
import org.example.traineeTrainers.TraineeTrainer;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerService;
import org.example.training.Training;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Api(value = "TraineeController", tags = "Trainee API")
@RestController
@RequestMapping("/api/trainee")

public class TraineeController {

	final TrainerService trainerService;

	private final UserService userService;

	final TraineeService traineeService;

	final TraineeTrainerService trainerTraineeService;

	public TraineeController(TrainerService trainerService, UserService userService, TraineeService traineeService,
			TraineeTrainerService trainerTraineeService) {
		this.trainerService = trainerService;
		this.userService = userService;
		this.traineeService = traineeService;
		this.trainerTraineeService = trainerTraineeService;
	}

	@ApiOperation(value = "Create a new trainee", response = ResponseEntity.class)
	@PostMapping("/post")
	public ResponseEntity post(@RequestBody PostTraineeDTO traineeDTO) {

		if (traineeDTO.getFirstname().equals(null) || traineeDTO.getLastname().equals(null)) {
			throw new RuntimeException("Firstname and lastnmae required");
		}
		User user = new User();
		user.setFirstName(traineeDTO.getFirstname());
		user.setLastName(traineeDTO.getLastname());
		User user1 = userService.create(user);
		Trainee trainee = new Trainee();
		trainee.setUser(user1);
		trainee.setAddress(traineeDTO.getAddress());
		trainee.setDateOfBirth(traineeDTO.getDob());
		Trainee trainee1 = traineeService.create(trainee);
		String username = trainee1.getUser().getUsername();
		String password = trainee1.getUser().getPassword();
		String response = "username:" + username + ", password :" + password;
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Get trainee by username", response = ResponseEntity.class)
	@GetMapping("/get")
	public ResponseEntity get(@RequestParam String username, String password) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		String response = traineeService.readByUsername(username).toString();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "update trainee", response = ResponseEntity.class)

	@PutMapping("/update")
	public ResponseEntity update(@RequestParam String username, String password,
			@RequestBody UpdateTraineeDTO updateTraineeDTO) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		if (updateTraineeDTO.getActive() == null || updateTraineeDTO.getFirstname() == null
				|| updateTraineeDTO.getLastname() == null || updateTraineeDTO.getUsername() == null) {
			throw new RuntimeException("required fields are empty");
		}

		User user1 = userService.readByUserName(updateTraineeDTO.getUsername());
		user1.setActive(updateTraineeDTO.getActive());
		user1.setLastName(updateTraineeDTO.getLastname());
		user1.setFirstName(updateTraineeDTO.getFirstname());
		userService.update(user1);
		Trainee trainee = traineeService.readByUsername(updateTraineeDTO.getUsername());
		if (updateTraineeDTO.getDob() != null) {
			trainee.setDateOfBirth(updateTraineeDTO.getDob());

		}
		if (updateTraineeDTO.getAddress() != null) {
			trainee.setAddress(updateTraineeDTO.getAddress());
		}
		traineeService.update(trainee);
		String response = updateTraineeDTO.toString();
		List<Trainer> trainers = new ArrayList<>();
		for (Training training : traineeService.getTraineeTrainingList(updateTraineeDTO.getUsername(), 1)) {
			trainers.add(training.getTrainer());
		}
		response = response + "trainers list: " + trainers;
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "delete trainee by username", response = ResponseEntity.class)

	@DeleteMapping("/delete")
	public ResponseEntity delete(@RequestParam String username, String password) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		traineeService.deleteByUsername(username);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "update trainees trainer list", response = ResponseEntity.class)

	@PutMapping("/updateTrainerList")
	public ResponseEntity updateTrainerList(@RequestParam String username, String password,
			@RequestBody List<String> trainersList) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		List<TraineeTrainer> traineeTrainers = new ArrayList<>();
		Trainee trainee = traineeService.readByUsername(username);
		for (String username1 : trainersList) {
			Trainer trainer = trainerService.readByUsername(username1);
			TraineeTrainer trainerTrainee = new TraineeTrainer();
			trainerTrainee.setTrainee(trainee);
			trainerTrainee.setTrainer(trainer);
			trainerTraineeService.add(trainerTrainee);
			traineeTrainers.add(trainerTrainee);
		}
		return ResponseEntity.ok(traineeTrainers);

	}

	@ApiOperation(value = "Get trainee's trainings'", response = ResponseEntity.class)
	@GetMapping("/getTraineeTrainings")

	public ResponseEntity getTraineeTrainings(@RequestParam String username, String password, LocalDate from,
			LocalDate to, String trainerName, Long trainingTypeId) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Trainee trainee = traineeService.readByUsername(username);
		List<Training> trainings = trainee.getTrainings();

		return ResponseEntity.ok(trainings);
	}

	@ApiOperation(value = "change trainees activation status", response = ResponseEntity.class)

	@PatchMapping("/activateDeacivate")
	public ResponseEntity changeStatus(@RequestParam String username, String password, Boolean bool) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Long id = traineeService.readByUsername(username).getId();
		traineeService.changeActivation(bool, id);
		return ResponseEntity.ok("");
	}

}
