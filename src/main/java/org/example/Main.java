package org.example;

import org.example.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
public class Main {

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Facade facade = context.getBean(Facade.class);
		// Initialization of Db and create profiles

		facade.addUser();
		facade.addTrainees();
		facade.addSpecialization();
		facade.addTrainers();
		facade.addTraining();
		facade.readAllUsers();
		facade.passwordChangeTrainee("123", 1l);

		// other task steps
		String usernameforAuthentication = "Azimjon.Alijonov";
		String password = "123";
		if (facade.validateUser(usernameforAuthentication, password)) {
			facade.traineeByUserName("Azimjon.Alijonov");
			facade.trainerByUserName("Azimjon3.Alijonov3");
			facade.passwordChangeTrainee("223", 1l);
			facade.passwordChangeTrainer("456", 1l);
			facade.updateTrainee();
			facade.updaTrainer();
			facade.changeActivationTrainee(true, 1l);
			facade.changeActivationTrainer(true, 1l);
			// facade.deleteTraineeByUsername("Azimjon2.Alijonov2");
			facade.getTraineeTrainingListByCriteria("Azimjon2.Alijonov2", 2);
			// add training is done above
			facade.getSpecificTrainers();
			facade.updateTraineesTrainerList();
		}
		else {
			throw new Exception("You are not authenticated");
		}

	}

}
