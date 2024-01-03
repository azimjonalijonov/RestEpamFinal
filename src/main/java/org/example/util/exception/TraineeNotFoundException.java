package org.example.util.exception;

import org.springframework.stereotype.Component;

public class TraineeNotFoundException extends RuntimeException {

	public TraineeNotFoundException(Long id) {
		super("Trainee not found with ID: " + id);
	}

}
