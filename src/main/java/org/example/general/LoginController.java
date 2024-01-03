package org.example.general;

import org.example.general.dto.ChangeLoginDTO;
import org.example.general.dto.LoginDTO;
import org.example.trainee.TraineeService;
import org.example.trainer.TrainerService;
import org.example.user.User;
import org.example.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	TraineeService traineeService;

	@Autowired
	TrainerService trainerService;

	@GetMapping("/login")
	public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
		if (loginDTO.getUsername().equals(null) || loginDTO.getPassword().equals(null)) {
			throw new RuntimeException("username or password should not be null");
		}
		User user = null;
		String username = loginDTO.getUsername();
		if (traineeService.readByUsername(username) != null) {
			user = traineeService.readByUsername(username).getUser();
		}
		else if (trainerService.readByUsername(username) != null) {

			user = trainerService.readByUsername(username).getUser();
		}
		else {
			throw new RuntimeException("no user with this username");
		}
		if (!user.getPassword().equals(loginDTO.getPassword())) {
			throw new RuntimeException("password mismatch");
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity update(@RequestBody ChangeLoginDTO changeLoginDTO) {
		if (changeLoginDTO.getNewPassword().equals(null) || changeLoginDTO.getOldPassword().equals(null)
				|| changeLoginDTO.getUsername().equals(null)) {
			throw new RuntimeException("old, new passwords and username must be not null");
		}
		if (userService.readByUserName(changeLoginDTO.getUsername()) == null) {
			throw new RuntimeException("user not found");
		}
		User user = userService.readByUserName(changeLoginDTO.getUsername());
		if (user.getPassword().equals(changeLoginDTO.getOldPassword())) {
			user.setPassword(changeLoginDTO.getNewPassword());
		}
		else {
			throw new RuntimeException("password incorrect");
		}
		userService.updatePassword(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
