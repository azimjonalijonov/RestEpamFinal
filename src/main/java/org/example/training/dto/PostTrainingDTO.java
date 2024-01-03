package org.example.training.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PostTrainingDTO {

	private String traineeUsername;

	private String trainerUsername;

	private String name;

	private LocalDateTime date;

	private Number duration;

	public String getTraineeUsername() {
		return traineeUsername;
	}

	public void setTraineeUsername(String traineeUsername) {
		this.traineeUsername = traineeUsername;
	}

	public String getTrainerUsername() {
		return trainerUsername;
	}

	public void setTrainerUsername(String trainerUsername) {
		this.trainerUsername = trainerUsername;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Number getDuration() {
		return duration;
	}

	public void setDuration(Number duration) {
		this.duration = duration;
	}

}
