package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.trainee.Trainee;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainer;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerService;
import org.example.training.Training;
import org.example.training.TrainingService;
import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeService;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class Facade {

	private static Logger logger = LogManager.getLogger(Facade.class);

	private final UserService userService;

	private final TraineeService traineeService;

	private final TrainerService trainerService;

	private final TrainingTypeService trainingTypeService;

	private final TrainingService trainingService;

	private final TraineeTrainerService traineeTrainerService;

	public Facade(UserService userService, TraineeService traineeService, TrainerService trainerService,
			TrainingTypeService trainingTypeService, TrainingService trainingService,
			TraineeTrainerService traineeTrainerService) {
		this.userService = userService;
		this.traineeService = traineeService;
		this.trainerService = trainerService;
		this.trainingTypeService = trainingTypeService;
		this.trainingService = trainingService;
		this.traineeTrainerService = traineeTrainerService;
	}

	public void readAllUsers() {
		List<User> list = userService.readAll();
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("these are users initialized by default");

		logger.info(list.toString());
		logger.info("---------------------------------------------------------------------------------------------");

	}

	public void readAllTrainees() {
		List<Trainee> list = traineeService.readAll();
		logger.info("these are trainees initialized by default");
		logger.info(list.toString());
		logger.info("---------------------------------------------------------------------------------------------");

	}

	public void readALLTrainers() {
		List<Trainer> list = trainerService.readAll();
		logger.info("these are trainers initialized by default");
		logger.info(list.toString());
		logger.info("---------------------------------------------------------------------------------------------");
	}

	public void readAllTrainings() {
		List<Training> list = trainingService.readAll();
		logger.info("these are trainings initialized by default");
		logger.info(list.toString());
		logger.info("---------------------------------------------------------------------------------------------");

	}

	public void readAllTrainingTypes() {
		List<TrainingType> list = trainingTypeService.readAll();
		logger.info("these are trainingTypes initialized by default");
		logger.info(list.toString());
		logger.info("---------------------------------------------------------------------------------------------");
	}

	public Trainee traineeByUserName(String username) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");

		logger.info("Trainee by username :");
		logger.info(traineeService.readByUsername(username).toString());

		return traineeService.readByUsername(username);
	}

	public Trainer trainerByUserName(String username) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("Trainer by username :");
		logger.info(trainerService.readByUsername(username).toString());

		return trainerService.readByUsername(username);
	}

	public void passwordChangeTrainee(String password, Long id) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info(traineeService.passwordChangeTrainee(password, id));

	}

	public void passwordChangeTrainer(String password, Long id) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		System.out.println(trainerService.passwordChangeTrainer(password, id));

	}

	public Trainee updateTrainee() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		Trainee trainee = traineeService.readById(1l);
		trainee.setAddress("Uzbekistan/Nmanagan");
		Trainee traineeUpdated = traineeService.update(trainee);
		logger.info(traineeUpdated.toString());

		return traineeUpdated;
	}

	public Trainer updaTrainer() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		Trainer trainer = trainerService.readById(1l);
		trainer.setSpecialization(trainingTypeService.readById(1l));
		Trainer trainerUpdated = trainerService.update(trainer);
		logger.info(trainerUpdated.toString());

		return trainerUpdated;
	}

	public void changeActivationTrainee(Boolean bool, Long id) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info(traineeService.changeActivation(bool, id));

	}

	public void changeActivationTrainer(Boolean bool, Long id) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info(trainerService.changeActivation(bool, id));

	}

	public void deleteTraineeByUsername(String username) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info(traineeService.deleteByUsername(username));

	}

	public List<Training> getTraineeTrainingListByCriteria(String username, Number duration) {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info(traineeService.getTraineeTrainingList(username, duration));
		return traineeService.getTraineeTrainingList(username, duration);
	}

	public List<Trainer> getSpecificTrainers() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		List<Trainer> trainers = trainerService.getSpecificTrainers();
		logger.info(trainers);
		return trainers;
	}

	public void updateTraineesTrainerList() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		// Lets add one trainer for trainee's trainer list
		Trainer trainer1 = trainerService.readById(3l);
		Trainee trainee = traineeService.readById(1l);
		List<TraineeTrainer> traineeTrainers = trainee.getTrainingSessions();
		TraineeTrainer traineeTrainer = new TraineeTrainer();
		traineeTrainer.setTrainer(trainer1);
		traineeTrainer.setTrainee(trainee);

		traineeTrainerService.add(traineeTrainer);
		traineeTrainers.add(traineeTrainer);
		trainee.setTrainingSessions(traineeTrainers);
		traineeService.create(trainee);
		logger.info("updated trainees trainerlist :" + traineeTrainers);

	}

	public void addUser() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		User user = new User();
		user.setFirstName("Azimjon");
		user.setLastName("Alijonov");
		user.setPassword("123");
		user.setActive(false);
		userService.create(user);
		logger.info(user.toString() + " is added___________________________________________________________________");
		logger.info("---------------------------------------------------------------------------------------------");

		User user2 = new User();
		user2.setFirstName("Azimjon2");
		user2.setLastName("Alijonov2");
		user2.setActive(false);
		userService.create(user2);
		logger.info(user2.toString() + " is added___________________________________________________________________");
		logger.info("---------------------------------------------------------------------------------------------");

		User user3 = new User();
		user3.setFirstName("Azimjon3");
		user3.setLastName("Alijonov3");
		user3.setActive(false);
		userService.create(user3);
		logger.info(user3.toString() + " is added___________________________________________________________________");
		User user4 = new User();
		user4.setFirstName("Azimjon4");
		user4.setLastName("Alijonov4");
		user4.setActive(true);
		userService.create(user4);
		logger.info(user4.toString() + " is added___________________________________________________________________");
		User user5 = new User();
		user5.setFirstName("Azimjon5");
		user5.setLastName("Alijonov5");
		user5.setActive(true);
		userService.create(user5);
		logger.info(user5.toString() + " is added___________________________________________________________________");

	}

	public void addTrainees() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		Trainee trainee = new Trainee();
		trainee.setUser(userService.readById(1l));

		traineeService.create(trainee);
		logger.info(trainee.toString() + " is added_______________________");
		Trainee trainee2 = new Trainee();
		trainee2.setUser(userService.readById(2l));
		traineeService.create(trainee2);

		logger.info(trainee2.toString() + " is added_______________________");
	}

	public void addSpecialization() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");

		TrainingType trainingType = new TrainingType();
		trainingType.setName("Circuit");
		trainingTypeService.add(trainingType);
		logger.info(trainingType.toString() + "  is added to database");
	}

	public void addTrainers() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");

		Trainer trainer = new Trainer();
		trainer.setUser(userService.readById(3l));
		trainer.setSpecialization(trainingTypeService.readById(1l));

		trainerService.create(trainer);
		logger.info(trainer.toString() + " is added_______________________");
		Trainer trainer2 = new Trainer();
		trainer2.setUser(userService.readById(4l));
		trainer2.setSpecialization(trainingTypeService.readById(1l));

		trainerService.create(trainer2);
		logger.info(trainer.toString() + " is added_______________________");
		Trainer trainer3 = new Trainer();
		trainer3.setUser(userService.readById(5l));
		trainer3.setSpecialization(trainingTypeService.readById(1l));

		trainerService.create(trainer3);
		logger.info(trainer3.toString() + " is added_______________________");

	}

	public void addTraining() {
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		Training training = new Training();
		training.setDuration(2);
		training.setTrainingDate(LocalDateTime.now());
		training.setTrainingName("name1");
		training.setTrainingType(trainingTypeService.readById(1l));
		training.setTrainee(traineeService.readById(1l));
		training.setTrainer(trainerService.readById(1l));
		trainingService.addTraining(training);
		logger.info(training.toString() + " ______________is added");

		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------------");
		Training training1 = new Training();
		training1.setDuration(2);
		training1.setTrainingDate(LocalDateTime.now());
		training1.setTrainingName("name2");
		training1.setTrainingType(trainingTypeService.readById(1l));
		training1.setTrainee(traineeService.readById(2l));
		training1.setTrainer(trainerService.readById(1l));
		trainingService.addTraining(training1);
		logger.info(training1.toString() + " ______________is added");
	}

	public Boolean validateUser(String usernname, String password) {
		User user = userService.readByUserName(usernname);
		if (user.equals(null)) {
			return false;
		}
		logger.info(
				user.getPassword() + "-------------------------------------------------------------------------------");
		return (user.getPassword().equals(password));
	}

}
