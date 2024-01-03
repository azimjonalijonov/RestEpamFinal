package org.example.trainee;

import org.example.interfaces.BaseService;
import org.example.training.Training;
import org.example.user.User;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.TraineeErrorValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraineeService implements BaseService<Trainee> {

	private final TraineeDAO traineeDAO;

	private final TraineeErrorValidator traineeErrorValidator;

	public TraineeService(TraineeDAO traineeDAO, TraineeErrorValidator traineeErrorValidator) {
		this.traineeDAO = traineeDAO;
		this.traineeErrorValidator = traineeErrorValidator;
	}

	@Override
	public List<Trainee> readAll() {
		List<Trainee> traineeMap = traineeDAO.readAll();
		if (traineeMap.isEmpty()) {
			System.out.println("The list of the trainee is empty");
		}
		return traineeMap;
	}

	@Override
	public Trainee readById(Long id) {
		return traineeDAO.readById(id);
	}

	@Override
	public Trainee create(Trainee createRequest) {
		if (traineeErrorValidator.isValidParamsForCreate(createRequest)) {
			traineeDAO.createOrUpdate(createRequest);
			return createRequest;
		}
		throw new ValidatorException("Something wrong with provided entity");
	}

	@Override
	public Trainee update(Trainee updateRequest) {
		if (traineeErrorValidator.isValidParamsForUpdate(updateRequest)) {
			traineeDAO.createOrUpdate(updateRequest);
			return updateRequest;
		}
		throw new ValidatorException("Something wrong with provided entity");
	}

	@Override
	public boolean deleteById(Long id) {
		return traineeDAO.deleteById(id);
	}

	public Trainee readByUsername(String username) {
		return traineeDAO.readByUsername(username);

	}

	public String passwordChangeTrainee(String password, Long id) {
		return traineeDAO.updatePassword(password, id);
	}

	public String changeActivation(Boolean bool, Long id) {
		return traineeDAO.changeActivation(bool, id);
	}

	public String deleteByUsername(String username) {
		return traineeDAO.deleteByUsername(username);
	}

	public List<Training> getTraineeTrainingList(String username, Number duration) {
		Trainee trainee = traineeDAO.readByUsername(username);
		List<Training> trainings = new ArrayList<>();
		for (Training training : trainee.getTrainings()) {
			// if (training.getDuration() == duration) {
			trainings.add(training);
			// }
		}
		return trainings;

	}

}
