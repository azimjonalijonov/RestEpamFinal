package org.example.trainer.dto;

import org.example.trainingType.dto.TrainingTypeDTO;

public class PostTrainerDTO {

	private String firstname;

	private String lastname;

	private TrainingTypeDTO trainingTypeDTO;

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

}
