package org.example.training;

import org.example.interfaces.BaseService;
import org.example.util.validation.impl.TrainingErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TrainingService implements BaseService<Training> {

	private final TrainingDAO trainingDAO;

	private final TrainingErrorValidator trainingErrorValidator;

	public TrainingService(TrainingDAO trainingDAO, TrainingErrorValidator trainingErrorValidator) {
		this.trainingDAO = trainingDAO;
		this.trainingErrorValidator = trainingErrorValidator;
	}

	@Override
	public List<Training> readAll() {
		return trainingDAO.readAll();
	}

	@Override
	public Training readById(Long id) {
		return trainingDAO.readById(id);
	}

	@Override
	public Training create(Training createRequest) {
		if (trainingErrorValidator.isValidParamsForCreate(createRequest)) {
			return trainingDAO.createOrUpdate(createRequest);
		}
		throw new RuntimeException("Some thing is wrong with provided entity");
	}

	@Override
	public Training update(Training updateRequest) {
		if (trainingErrorValidator.isValidParamsForUpdate(updateRequest)) {
			return trainingDAO.createOrUpdate(updateRequest);
		}
		throw new RuntimeException("Some thing is wrong with provided entity");
	}

	@Override
	public boolean deleteById(Long id) {
		return trainingDAO.deleteById(id);
	}

	public Training addTraining(Training training) {
		return trainingDAO.createOrUpdate(training);

	}

}
