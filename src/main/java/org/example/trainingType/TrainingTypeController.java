package org.example.trainingType;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("trainingType")
public class TrainingTypeController {

	@Autowired
	TrainingTypeService trainingTypeService;
	@ApiOperation(value = "get trainingtype", response = ResponseEntity.class)

	@GetMapping("/get")
	public ResponseEntity get() {
		List<TrainingType> trainingTypeList = trainingTypeService.readAll();
		return ResponseEntity.ok(trainingTypeList);

	}

}
