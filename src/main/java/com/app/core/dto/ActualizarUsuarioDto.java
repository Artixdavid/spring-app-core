package com.app.core.dto;

import java.sql.Date;

import com.app.core.models.entity.Status;
import com.app.core.models.entity.User;

public class ActualizarUsuarioDto {

	private String firstName;
	private String lastName;
	private String motherLastName;
	private String email;
	private String phone;
	private Status status;
	private Date createAt;
	private String aboutMe;
	private String secondName;

	private Date fechaNacimiento;
	private String address;
	private String phonoEx;

	public ActualizarUsuarioDto() {

	}

	public ActualizarUsuarioDto(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.motherLastName = user.getMotherLastName();

		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.status = user.getStatus();
		this.createAt = (Date) user.getCreateAt();
		this.aboutMe = user.getAboutMe();

		this.secondName = user.getSecondName();
		this.fechaNacimiento = (Date) user.getFechaNacimiento();
		this.address = user.getAddress();
		this.phonoEx = user.getPhonoEx();

	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMotherLastName() {
		return motherLastName;
	}

	public void setMotherLastName(String motherLastName) {
		this.motherLastName = motherLastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonoEx() {
		return phonoEx;
	}

	public void setPhonoEx(String phonoEx) {
		this.phonoEx = phonoEx;
	}

}
