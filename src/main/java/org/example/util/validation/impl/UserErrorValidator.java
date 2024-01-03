package org.example.util.validation.impl;

import org.example.user.User;
import org.example.util.exception.ValidatorException;
import org.example.util.validation.Validator;

public class UserErrorValidator implements Validator<User> {

	@Override
	public boolean isValidParamsForCreate(User entity) {
		if (entity.getFirstName() == null) {
			throw new ValidatorException("FirstName should not be null");
		}
		else if (entity.getLastName() == null) {
			throw new ValidatorException("LastName should not be null");

		}
		// else if (entity.getActive() == null) {
		// throw new ValidatorException("isActive should not be null");
		// }
		return true;
	}

	@Override
	public boolean isValidParamsForUpdate(User entity) {
		if (entity.getFirstName() == null) {
			throw new ValidatorException("FirstName should not be null");
		}
		else if (entity.getId() == null) {
			throw new ValidatorException("id should not be null");
		}
		else if (entity.getLastName() == null) {
			throw new ValidatorException("LastName should not be null");
		}
		else if (entity.getUsername() == null) {
			throw new ValidatorException("Username should not be null");
		}
		else if (entity.getPassword() == null) {
			throw new ValidatorException("Password should not be null");
		}
		// else if (entity.getActive() == null) {
		// throw new ValidatorException("isActive should not be null");
		// }
		return true;
	}

}
