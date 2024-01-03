package org.example.util.validation.impl;

import org.example.trainingType.TrainingType;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.Validator;

public class TrainingTypeErrorValidator implements Validator<TrainingType> {

	@Override
	public boolean isValidParamsForCreate(TrainingType entity) {

		if (entity.getName() == null) {
			throw new ValidatorException("The name of the type cannot be null");
		}
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(TrainingType entity) {

		if (entity.getId() == null) {
			throw new ValidatorException("ID cannot be null");
		}
		else if (entity.getName() == null) {
			throw new ValidatorException("The name of the type cannot be null");
		}
		return true;
	}

}
