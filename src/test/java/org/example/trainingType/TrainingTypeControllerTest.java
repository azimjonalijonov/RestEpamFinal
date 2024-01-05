package org.example.trainingType;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @Test
    void testGet() {
         TrainingType trainingType1 = new TrainingType();
        trainingType1.setId(1L);
        trainingType1.setName("Type1");

        TrainingType trainingType2 = new TrainingType();
        trainingType2.setId(2L);
        trainingType2.setName("Type2");

        List<TrainingType> trainingTypeList = Arrays.asList(trainingType1, trainingType2);

         when(trainingTypeService.readAll()).thenReturn(trainingTypeList);

         ResponseEntity responseEntity = trainingTypeController.get();

          assertEquals(ResponseEntity.ok(trainingTypeList), responseEntity);
    }
}
