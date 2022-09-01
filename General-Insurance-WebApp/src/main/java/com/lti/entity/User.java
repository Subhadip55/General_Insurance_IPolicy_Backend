package com.lti.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "NAME")
	private String user_name;

	@Column(name = "DRIVING_LICENSE")
	private String user_dl;

	@Column(name = "DATEOFBIRTH")
	private LocalDate user_dob;

	@Column(name = "CONTACTNO")
	private long user_contactNo;

	@Column(name = "PASSWORD")
	private String user_password;

	@Column(name = "CITY")
	private String user_city;

	@Column(name = "STATE")
	private String user_state;

	@Column(name = "ZIPCODE")
	private int user_zipCode;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_dl() {
		return user_dl;
	}

	public void setUser_dl(String user_dl) {
		this.user_dl = user_dl;
	}

	public LocalDate getUser_dob() {
		return user_dob;
	}

	public void setUser_dob(LocalDate user_dob) {
		this.user_dob = user_dob;
	}

	public long getUser_contactNo() {
		return user_contactNo;
	}

	public void setUser_contactNo(long user_contactNo) {
		this.user_contactNo = user_contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_city() {
		return user_city;
	}

	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}

	public String getUser_state() {
		return user_state;
	}

	public void setUser_state(String user_state) {
		this.user_state = user_state;
	}

	public int getUser_zipCode() {
		return user_zipCode;
	}

	public void setUser_zipCode(int user_zipCode) {
		this.user_zipCode = user_zipCode;
	}

	@Override
	public String toString() {
		return "User [user_name=" + user_name + ", user_dl=" + user_dl + ", user_dob=" + user_dob + ", user_contactNo="
				+ user_contactNo + ", email=" + email + ", user_password=" + user_password + ", user_city=" + user_city
				+ ", user_state=" + user_state + ", user_zipCode=" + user_zipCode
				+ /* ", motorList=" + motorList + */ "]";
	}

}
