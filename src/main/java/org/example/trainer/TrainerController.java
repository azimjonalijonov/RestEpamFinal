package org.example.trainer;

import org.example.trainee.Trainee;
import org.example.trainee.TraineeService;
import org.example.trainee.dto.PostTraineeDTO;
import org.example.traineeTrainers.TraineeTrainer;
import org.example.trainer.dto.PostTrainerDTO;
import org.example.trainer.dto.UpdateTrainerDTO;
import org.example.training.Training;
import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeService;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

	@Autowired
	TraineeService traineeService;

	@Autowired
	UserService userService;

	@Autowired
	TrainerService trainerService;

	@Autowired
	TrainingTypeService trainingTypeService;

	@PostMapping("/post")
	public ResponseEntity post(@RequestBody PostTrainerDTO postTrainerDTO) {
		if (postTrainerDTO.getFirstname().equals(null) || postTrainerDTO.getLastname().equals(null)
				|| postTrainerDTO.getTrainingTypeDTO() == null) {
			throw new RuntimeException("Firstname and lastnmae required");
		}
		User user = new User();
		user.setFirstName(postTrainerDTO.getFirstname());
		user.setLastName(postTrainerDTO.getLastname());
		User user1 = userService.create(user);
		TrainingType trainingType = trainingTypeService.readById(postTrainerDTO.getTrainingTypeDTO().getId());
		Trainer trainer = new Trainer();
		trainer.setSpecialization(trainingType);
		trainer.setUser(user1);
		trainerService.create(trainer);
		String username = trainer.getUser().getUsername();
		String password = trainer.getUser().getPassword();
		String response = "username:" + username + ", password :" + password;
		return ResponseEntity.ok(response);

	}

	@GetMapping("/get")
	public ResponseEntity get(@RequestParam String username, String password) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		String response = trainerService.readByUsername(username).toString();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity update(@RequestParam String username, String password,
			@RequestBody UpdateTrainerDTO updateTrainerDTO) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		User user1 = userService.readByUserName(updateTrainerDTO.getUsername());
		user1.setActive(updateTrainerDTO.getActive());
		user1.setLastName(updateTrainerDTO.getLastname());
		user1.setFirstName(updateTrainerDTO.getFirstname());
		userService.update(user1);
		List<Trainee> trainees = new ArrayList<>();
		for (TraineeTrainer training : trainerService.getTraineeTrainingList(updateTrainerDTO.getUsername(), 1)) {
			trainees.add(training.getTrainee());
		}
		String response = updateTrainerDTO.toString();
		response = response + "trainees list: " + trainees;
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getspecial")
	public ResponseEntity getSpecial(@RequestParam String username, String password) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		List<Trainer> trainerList = trainerService.getSpecificTrainers();
		return ResponseEntity.ok(trainerList);
	}

	@PatchMapping("/activateDeacivate")
	public ResponseEntity changeStatus(@RequestParam String username, String password, Boolean bool) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		Long id = trainerService.readByUsername(username).getId();
		trainerService.changeActivation(bool, id);
		return ResponseEntity.ok("");
	}

}
