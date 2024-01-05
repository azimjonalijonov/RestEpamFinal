package org.example.training;

import static org.junit.jupiter.api.Assertions.*;

import org.example.trainee.Trainee;
import org.example.trainee.TraineeService;
import org.example.traineeTrainers.TraineeTrainerService;
import org.example.trainer.Trainer;
import org.example.trainer.TrainerService;
import org.example.training.dto.PostTrainingDTO;
import org.example.user.User;
import org.example.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TrainingControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingService trainingService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeTrainerService trainerTraineeService;

    @InjectMocks
    private TrainingController trainingController;

    @Test
    void testPost() {
         PostTrainingDTO postTrainingDTO = new PostTrainingDTO();
        postTrainingDTO.setTraineeUsername("Azimjon1.Alijonov1");
        postTrainingDTO.setTrainerUsername("Azimjon4.Alijonov4");
        postTrainingDTO.setDuration(2);
        postTrainingDTO.setDate(LocalDateTime.MIN.now());
        postTrainingDTO.setName("smth");

         when(userService.readByUserName(any(String.class))).thenReturn(new User());
        when(traineeService.readByUsername(any(String.class))).thenReturn(new Trainee());
        when(trainerService.readByUsername(any(String.class))).thenReturn(new Trainer());
        when(trainingService.addTraining(any(Training.class))).thenReturn(new Training());

         ResponseEntity responseEntity = trainingController.post("Azimjon1.Alijonov1", "123", postTrainingDTO);

         assertEquals(ResponseEntity.ok(new Training().toString()), responseEntity);
    }
}
