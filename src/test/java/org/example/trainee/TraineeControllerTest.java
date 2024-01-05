package org.example.trainee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.trainee.dto.PostTraineeDTO;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.TrainerDAO;
import org.example.trainer.TrainerService;
import org.example.user.User;
import org.example.user.UserDAO;
import org.example.user.UserService;
import org.example.util.validation.impl.TraineeErrorValidator;
import org.example.util.validation.impl.TrainerErrorValidator;
import org.example.util.validation.impl.UserErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeTrainerService trainerTraineeService;

    @InjectMocks
    private TraineeController traineeController;
    private UserDAO userDAOMock;

    private UserErrorValidator userErrorValidatorMock;

    private TraineeDAO traineeDAOMock;

    private TraineeErrorValidator traineeErrorValidatorMock;


    // Your other dependencies go here
    @BeforeEach
    void setUp() {
        traineeDAOMock = mock(TraineeDAO.class);
        traineeErrorValidatorMock = mock(TraineeErrorValidator.class);
        traineeService = new TraineeService(traineeDAOMock, traineeErrorValidatorMock);
        userDAOMock = mock(UserDAO.class);
        userErrorValidatorMock = mock(UserErrorValidator.class);
        userService = new UserService(userDAOMock, userErrorValidatorMock);
        traineeController=new TraineeController();
    }
    @Test
    void testPost() {
         PostTraineeDTO postTraineeDTO = new PostTraineeDTO();
        postTraineeDTO.setFirstname("John");
        postTraineeDTO.setLastname("Doe");
         when(userService.create(any(User.class))).thenReturn(new User());
        when(traineeService.create(any(Trainee.class))).thenReturn(new Trainee());

         ResponseEntity responseEntity = traineeController.post(postTraineeDTO);

         assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGet() {
         String username = "john.doe";
        String password = "password";
         when(userService.readByUserName(username)).thenReturn(new User());
        when(traineeService.readByUsername(username)).thenReturn(new Trainee());

         ResponseEntity responseEntity = traineeController.get(username, password);

         assertEquals(ResponseEntity.ok().build(), responseEntity);
    }


}
