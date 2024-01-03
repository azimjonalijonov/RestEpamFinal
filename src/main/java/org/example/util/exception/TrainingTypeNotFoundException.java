package org.example.util.exception;

import org.springframework.stereotype.Component;

public class TrainingTypeNotFoundException extends RuntimeException {

	public TrainingTypeNotFoundException(Long id) {
		super("Training type not found with ID: " + id);
	}

}
