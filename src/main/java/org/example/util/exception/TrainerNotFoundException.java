package org.example.util.exception;

import org.springframework.stereotype.Component;

public class TrainerNotFoundException extends RuntimeException {

	public TrainerNotFoundException(Long id) {
		super("Trainer not found with ID: " + id);
	}

}
