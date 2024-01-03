package org.example.traineeTrainers;

import jakarta.persistence.*;
import org.example.trainee.Trainee;
import org.example.trainer.Trainer;

@Entity
@Table(name = "trainer_trainee")
public class TraineeTrainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	@ManyToOne
	@JoinColumn(name = "trainee_id")
	private Trainee trainee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

}