package org.example.trainer.dto;

import org.example.trainingType.dto.TrainingTypeDTO;

public class UpdateTrainerDTO {

	private String username;

	private String firstname;

	private String lastname;

	private TrainingTypeDTO trainingTypeDTO;

	private Boolean isActive;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public TrainingTypeDTO getTrainingTypeDTO() {
		return trainingTypeDTO;
	}

	public void setTrainingTypeDTO(TrainingTypeDTO trainingTypeDTO) {
		this.trainingTypeDTO = trainingTypeDTO;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

}
