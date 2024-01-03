package org.example.trainingType;

import org.example.interfaces.BaseService;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.impl.TrainingTypeErrorValidator;

import java.util.List;;

public class TrainingTypeService {

	private final TrainingTypeDAO trainingTypeDAO;

	private final TrainingTypeErrorValidator trainingTypeErrorValidator;

	public TrainingTypeService(TrainingTypeDAO trainingTypeDAO, TrainingTypeErrorValidator trainingTypeErrorValidator) {
		this.trainingTypeDAO = trainingTypeDAO;
		this.trainingTypeErrorValidator = trainingTypeErrorValidator;
	}

	public List<TrainingType> readAll() {
		return trainingTypeDAO.readAll();
	}

	public TrainingType readById(Long id) {
		return trainingTypeDAO.readById(id);
	}

	public TrainingType add(TrainingType trainingType) {
		if (trainingTypeErrorValidator.isValidParamsForCreate(trainingType)) {
			return trainingTypeDAO.createOrUpdate(trainingType);
		}
		throw new ValidatorException("Something wrong with validator");
	}

}
