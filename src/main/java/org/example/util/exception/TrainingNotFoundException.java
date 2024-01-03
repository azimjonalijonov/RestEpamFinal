package org.example.util.exception;

import org.springframework.stereotype.Component;

public class TrainingNotFoundException extends RuntimeException {

	public TrainingNotFoundException(Long id) {
		super("Training not found with ID: " + id);
	}

}
