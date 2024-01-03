package org.example.trainer;

import org.example.interfaces.BaseService;
import org.example.trainee.Trainee;
import org.example.traineeTrainers.TraineeTrainer;
import org.example.training.Training;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.TrainerErrorValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrainerService implements BaseService<Trainer> {

	private final TrainerDAO trainerDAO;

	private final TrainerErrorValidator trainerErrorValidator;

	public TrainerService(TrainerDAO trainerDAO, TrainerErrorValidator trainerErrorValidator) {
		this.trainerDAO = trainerDAO;
		this.trainerErrorValidator = trainerErrorValidator;
	}

	@Override
	public List<Trainer> readAll() {
		return trainerDAO.readAll();
	}

	@Override
	public Trainer readById(Long id) {
		return trainerDAO.readById(id);
	}

	@Override
	public Trainer create(Trainer createRequest) {
		if (trainerErrorValidator.isValidParamsForCreate(createRequest)) {
			return trainerDAO.createOrUpdate(createRequest);
		}
		throw new RuntimeException("Some thing wrong validator");
	}

	@Override
	public Trainer update(Trainer updateRequest) {
		if (trainerErrorValidator.isValidParamsForUpdate(updateRequest)) {
			return trainerDAO.createOrUpdate(updateRequest);
		}
		throw new ValidatorException("SOme thing wrong with provided entity");
	}

	@Override
	public boolean deleteById(Long id) {
		return trainerDAO.deleteById(id);
	}

	public Trainer readByUsername(String username) {
		return trainerDAO.readByUsername(username);

	}

	public String passwordChangeTrainer(String password, Long id) {
		return trainerDAO.updatePassword(password, id);

	}

	public String changeActivation(Boolean bool, Long id) {
		return trainerDAO.changeActivation(bool, id);
	}

	public List<Trainer> getSpecificTrainers() {
		return trainerDAO.findActiveTrainersWithNoAssignees();
	}

	public List<TraineeTrainer> getTraineeTrainingList(String username, Number duration) {
		Trainer trainer = trainerDAO.readByUsername(username);
		List<TraineeTrainer> trainings = new ArrayList<>();
		for (TraineeTrainer training : trainer.getTrainingSessions()) {
			trainings.add(training);
			// }
		}
		return trainings;

	}

}