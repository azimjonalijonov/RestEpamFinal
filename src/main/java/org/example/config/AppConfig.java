package org.example.config;

import org.example.Facade;
import org.example.trainee.Trainee;
import org.example.trainee.TraineeDAO;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainerDAO;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerDAO;
import org.example.trainer.TrainerService;
import org.example.training.Training;
import org.example.training.TrainingDAO;
import org.example.training.TrainingService;
import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeDAO;
import org.example.trainingType.TrainingTypeService;
import org.example.user.User;
import org.example.user.UserDAO;
import org.example.user.UserService;
import org.example.util.validation.impl.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
// @EnableWebMvc
//@ComponentScan(basePackages = "org.example")
public class AppConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.example"))
				.paths(PathSelectors.any())
				.build();
	}
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false)
			.favorParameter(true)
			.parameterName("mediaType")
			.ignoreAcceptHeader(true)
			.useJaf(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
			.mediaType("xml", MediaType.APPLICATION_XML)
			.mediaType("json", MediaType.APPLICATION_JSON);
	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws IOException {
		return new HibernateTransactionManager(sessionFactory());
	}
	// @Bean
	// public SessionFactory sessionFactory() {
	// return new MetadataSources(new
	// StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build())
	// .buildMetadata()
	// .buildSessionFactory();
	// }

	@Bean
	public SessionFactory sessionFactory() throws IOException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("org.example.trainee", "org.example.trainer",
				"org.example.traineeTrainers", "org.example.training", "org.example.trainingType", "org.example.user");
		sessionFactoryBean.setHibernateProperties(hibernateProperties());
		sessionFactoryBean.afterPropertiesSet();
		return sessionFactoryBean.getObject();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		return hibernateProperties;
	}

	@Bean
	public TraineeDAO traineeDao() throws IOException {
		return new TraineeDAO(sessionFactory());
	}

	@Bean
	public TrainerDAO trainerDAO() throws IOException {
		return new TrainerDAO(userDAO(), sessionFactory());
	}

	@Bean
	public User getUser() {
		return new User();
	}

	@Bean
	public Training getTraining() {
		return new Training();
	}

	@Bean
	public Trainer getTrainer() {
		return new Trainer();
	}

	@Bean
	public Trainee gettrainee() {
		return new Trainee();
	}

	@Bean
	public TraineeDAO traineeDAO() throws IOException {
		return new TraineeDAO(sessionFactory());
	}

	@Bean
	public TraineeErrorValidator traineeErrorValidator() {
		return new TraineeErrorValidator();
	}

	@Bean
	public TrainingType getTrainingType() {
		return new TrainingType();
	}

	@Bean
	public UserDAO userDAO() throws IOException {
		return new UserDAO(sessionFactory());

	}

	@Bean
	public TraineeTrainerDAO traineeTrainer() throws IOException {
		return new TraineeTrainerDAO(sessionFactory());
	}

	@Bean
	public UserErrorValidator userErrorValidator() {
		return new UserErrorValidator();

	}

	@Bean
	public TrainerErrorValidator trainerErrorValidator() {
		return new TrainerErrorValidator();
	}

	@Bean
	public TrainingDAO trainingDAO() throws IOException {
		return new TrainingDAO(sessionFactory());
	}

	@Bean
	public TrainingErrorValidator trainingErrorValidator() {
		return new TrainingErrorValidator();
	}

	@Bean
	public TrainingTypeDAO trainingTypeDAO() throws IOException {
		return new TrainingTypeDAO(sessionFactory());
	}

	@Bean
	public TrainingTypeErrorValidator trainingTypeErrorValidator() {
		return new TrainingTypeErrorValidator();
	}

	@Bean
	public UserService userService() throws IOException {
		return new UserService(userDAO(), userErrorValidator());
	}

	@Bean
	public TraineeService traineeService() throws IOException {
		return new TraineeService(traineeDAO(), traineeErrorValidator());
	}

	@Bean
	public TrainerService trainerService() throws IOException {
		return new TrainerService(new TrainerDAO(userDAO(), sessionFactory()), trainerErrorValidator());
	}

	@Bean
	public TrainingService trainingService() throws IOException {
		return new TrainingService(trainingDAO(), trainingErrorValidator());
	}

	@Bean
	public TrainingTypeService trainingTypeService() throws IOException {
		return new TrainingTypeService(trainingTypeDAO(), trainingTypeErrorValidator());
	}

	@Bean
	public TraineeTrainerService treaineeeTrainerService() throws IOException {
		return new TraineeTrainerService(traineeTrainer());
	}

	@Bean
	public Facade facade(UserService userService, TraineeService traineeService, TrainerService trainerService,
			TrainingService trainingService, TrainingTypeService trainingTypeService,
			TraineeTrainerService traineeTrainerService) {
		return new Facade(userService, traineeService, trainerService, trainingTypeService, trainingService,
				traineeTrainerService);
	}

}
