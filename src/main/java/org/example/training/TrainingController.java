package org.example.training;

import io.swagger.annotations.ApiOperation;
import org.example.trainee.Trainee;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerService;
import org.example.training.dto.PostTrainingDTO;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.MediaSize;

@RestController
@RequestMapping("/api/training")
public class TrainingController {

	@Autowired
	UserService userService;

	@Autowired
	TraineeService traineeService;

	@Autowired
	TrainingService trainingService;

	@Autowired
	TrainerService trainerService;

	@Autowired
	TraineeTrainerService trainerTraineeService;
	@ApiOperation(value = "post training", response = ResponseEntity.class)

	@PostMapping("/post")
	public ResponseEntity post(@RequestParam String username, String password,
			@RequestBody PostTrainingDTO postTraining) {
		if (userService.readByUserName(username) == null) {
			throw new RuntimeException("user does not exist");
		}
		User user = userService.readByUserName(username);
		if (!user.getPassword().equals(password)) {
			throw new RuntimeException("wrong password");
		}
		if (postTraining.getTrainerUsername() == null || postTraining.getTraineeUsername() == null
				|| postTraining.getDuration() == null || postTraining.getDate() == null
				|| postTraining.getName() == null) {
			throw new RuntimeException("all fields must be filled");
		}
		Trainee trainee = traineeService.readByUsername(postTraining.getTraineeUsername());
		Trainer trainer = trainerService.readByUsername(postTraining.getTrainerUsername());

		Training training = new Training();
		training.setTrainer(trainer);
		training.setTrainingName(postTraining.getName());
		training.setTrainee(trainee);
		training.setDuration(postTraining.getDuration());
		training.setTrainingDate(postTraining.getDate());
		trainingService.addTraining(training);
		return ResponseEntity.ok(training.toString());

	}

}
