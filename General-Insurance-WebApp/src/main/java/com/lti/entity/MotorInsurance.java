package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "MOTOR_INSURANCE")
public class MotorInsurance {

	@Id
	@SequenceGenerator(name = "policyNoSeqGen", sequenceName = "policy_no_seq", initialValue = 101, allocationSize = 1)
	@GeneratedValue(generator = "policyNoSeqGen")
	@Column(name = "POLICY_NO")
	private int policy_no;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "MODEL")
	private String model;

	private LocalDate dateOfExpiry;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "PURCHASE_DATE")
	private LocalDate purchase_date;

	@Column(name = "REGISTRATION_NO")
	private String registration_no;

	@Column(name = "ENGINE_NO")
	private String engine_no;

	@Column(name = "CHASIS_NO")
	private String chasis_no;

	@Column(name = "PLAN")
	private String plan;

	@Column(name = "YEAR")
	private int year;

	@Column(name = "CLAIMED")
	private boolean claimed;

	@Column(name = "APPROVED")
	private boolean approved;

	@OneToOne
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	private Payment payment;

	private String message;

	public MotorInsurance() {
		this.message = "Pending Approval";
		this.approved = false;
		this.claimed = false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public int getPolicy_no() {
		return policy_no;
	}

	public void setPolicy_no(int policy_no) {
		this.policy_no = policy_no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public LocalDate getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(LocalDate dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public LocalDate getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(LocalDate purchase_date) {
		this.purchase_date = purchase_date;
	}

	public String getRegistration_no() {
		return registration_no;
	}

	public void setRegistration_no(String registration_no) {
		this.registration_no = registration_no;
	}

	public String getEngine_no() {
		return engine_no;
	}

	public void setEngine_no(String engine_no) {
		this.engine_no = engine_no;
	}

	public String getChasis_no() {
		return chasis_no;
	}

	public void setChasis_no(String chasis_no) {
		this.chasis_no = chasis_no;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(boolean claimed) {
		this.claimed = claimed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}