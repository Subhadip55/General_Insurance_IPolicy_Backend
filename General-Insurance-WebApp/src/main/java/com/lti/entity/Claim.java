package com.lti.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Claim {

	@Id
	@SequenceGenerator(name = "claimSeqGen", sequenceName = "mySeq", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(generator = "claimSeqGen")
	private int claim_no;
	private LocalDate date_of_apply;
	private boolean approved;
	private String accident_type;
	private int amount;
	private String message;

	@Autowired
	@OneToOne(cascade = CascadeType.ALL)
	private MotorInsurance insurance;

	public Claim() {
		this.approved = false;
		this.amount = 0;
		this.message = "Pending";
	}

	public int getClaim_no() {
		return claim_no;
	}

	public void setClaim_no(int claim_no) {
		this.claim_no = claim_no;
	}

	public LocalDate getDate_of_apply() {
		return date_of_apply;
	}

	public void setDate_of_apply(LocalDate date_of_apply) {
		this.date_of_apply = date_of_apply;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getAccident_type() {
		return accident_type;
	}

	public void setAccident_type(String accident_type) {
		this.accident_type = accident_type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MotorInsurance getInsurance() {
		return insurance;
	}

	public void setInsurance(MotorInsurance insurance) {
		this.insurance = insurance;
	}

}