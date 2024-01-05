package org.example.trainer;

import static org.junit.jupiter.api.Assertions.*;

import org.example.trainee.TraineeService;
import org.example.trainer.dto.PostTrainerDTO;
import org.example.trainer.dto.UpdateTrainerDTO;
import org.example.trainingType.TrainingType;
import org.example.trainingType.TrainingTypeService;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainerControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private UserService userService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainerController trainerController;

    @Test
    void testPost() {
         PostTrainerDTO postTrainerDTO = new PostTrainerDTO();
        postTrainerDTO.setFirstname("Azimjon");
        postTrainerDTO.setLastname("Alijonov");

         when(userService.create(any(User.class))).thenReturn(new User());
        when(trainingTypeService.readById(any(Long.class))).thenReturn(new TrainingType());
        when(trainerService.create(any(Trainer.class))).thenReturn(new Trainer());

         ResponseEntity responseEntity = trainerController.post(postTrainerDTO);

         assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGet() {
         String username = "Azimjon.Alijonov";
        String password = "123";

         when(userService.readByUserName(username)).thenReturn(new User());
        when(trainerService.readByUsername(username)).thenReturn(new Trainer());

         ResponseEntity responseEntity = trainerController.get(username, password);

         assertEquals(ResponseEntity.ok().build(), responseEntity);
    }
    @Test
    void testUpdate() {
         UpdateTrainerDTO updateTrainerDTO = new UpdateTrainerDTO();
        updateTrainerDTO.setFirstname("Azimjon");
            updateTrainerDTO.setLastname("Alijonov");
        updateTrainerDTO.setUsername("Azimjon.Alijonov");
        updateTrainerDTO.setActive(true);


        when(userService.readByUserName(any(String.class))).thenReturn(new User());
        when(trainerService.getTraineeTrainingList(any(String.class), any(Integer.class))).thenReturn(new ArrayList<>());
        when(userService.update(any(User.class))).thenReturn(new User());

         ResponseEntity responseEntity = trainerController.update("Azimjon.Alijonov", "123", updateTrainerDTO);

         assertEquals(ResponseEntity.ok().build(), responseEntity);
    }

    @Test
    void testGetSpecial() {
         String username = "Azimjon.Alijonov";

         when(userService.readByUserName(any(String.class))).thenReturn(new User());
        when(trainerService.getSpecificTrainers()).thenReturn(new ArrayList<>());

         ResponseEntity responseEntity = trainerController.getSpecial(username, "123");

         assertEquals(ResponseEntity.ok(new ArrayList<>()), responseEntity);
    }



}
