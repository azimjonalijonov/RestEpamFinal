package org.example.util.validation.impl;

import org.example.trainer.Trainer;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.Validator;
import org.springframework.stereotype.Component;

public class TrainerErrorValidator implements Validator<Trainer> {

	@Override
	public boolean isValidParamsForCreate(Trainer entity) {
		if (entity.getUser() == null) {
			throw new ValidatorException("Some thing wrong with user_id");
		}
		else if (entity.getSpecialization() == null) {
			throw new ValidatorException("Some thing wrong with specialization");
		}
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(Trainer entity) {
		if (entity.getId() == null) {
			throw new ValidatorException("Some thing wrong with id");
		}
		else if (entity.getUser() == null) {
			throw new ValidatorException("Some thing wrong with user_id");
		}
		else if (entity.getSpecialization() == null) {
			throw new ValidatorException("Some thing wrong with specialization");
		}
		return true;
	}

}
