package org.example.util.validation.impl;

import org.example.trainee.Trainee;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.Validator;
import org.springframework.stereotype.Component;

public class TraineeErrorValidator implements Validator<Trainee> {

	@Override
	public boolean isValidParamsForCreate(Trainee entity) {

		// if (entity.getUser() == null) {
		// throw new ValidatorException("Illegal argument with user id (it can be null or
		// not long)");
		// }
		// else if (entity.getAddress() == null) {
		// throw new ValidatorException("Address should be specified)");
		// }
		// else if (entity.getDateOfBirth() == null) {
		// throw new ValidatorException("Date of birth should be specified)");
		// }
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(Trainee entity) {

		if (entity.getId() == null) {
			throw new ValidatorException("Illegal argument with id (it can be null or not long)");
		}
		else if (entity.getUser() == null) {
			throw new ValidatorException("Illegal argument with user id (it can be null or not long)");
		}
		// else if (entity.getAddress() == null) {
		// throw new ValidatorException("Address should be specified)");
		// }
		// else if (entity.getDateOfBirth() == null) {
		// throw new ValidatorException("Date of birth should be specified)");
		// }
		return true;

	}

}