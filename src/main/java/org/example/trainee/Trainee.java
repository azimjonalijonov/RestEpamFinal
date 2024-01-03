package org.example.trainee;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import org.example.traineeTrainers.TraineeTrainer;
import org.example.training.Training;
import org.example.user.User;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.DETACH;

@Entity
public class Trainee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate dateOfBirth;

	private String address;

	public List<TraineeTrainer> getTrainingSessions() {
		return trainingSessions;
	}

	public void setTrainingSessions(List<TraineeTrainer> trainingSessions) {
		this.trainingSessions = trainingSessions;
	}

	@OneToOne(cascade = ALL)
	private User user;

	@OneToMany(mappedBy = "trainee", cascade = DETACH, fetch = FetchType.EAGER)
	private List<Training> trainings;

	@OneToMany(mappedBy = "trainee", fetch = FetchType.EAGER)
	private List<TraineeTrainer> trainingSessions;

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Trainee() {

	}

	@Override
	public String toString() {
		return "Trainee{" + "id=" + id + ", dateOfBirth=" + dateOfBirth + ", address='" + address + '\'' + ", user="
				+ user + ", trainingsSize=" + trainings + ", trainingSessionsSize=" + trainingSessions + '}';
	}

	public Trainee(Long id, LocalDate dateOfBirth, String address, User user) {
		this.id = id;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.user = user;
	}

}
