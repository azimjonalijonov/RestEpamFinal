package org.example.trainee.dto;

import java.time.LocalDate;

public class UpdateTraineeDTO {

	private String username;

	private String firstname;

	private String lastname;

	private LocalDate dob;

	private String address;

	@Override
	public String toString() {
		return "UpdateTraineeDTO{" + "username='" + username + '\'' + ", firstname='" + firstname + '\''
				+ ", lastname='" + lastname + '\'' + ", dob=" + dob + ", address='" + address + '\'' + ", isActive="
				+ isActive + '}';
	}

	private Boolean isActive;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}

}
