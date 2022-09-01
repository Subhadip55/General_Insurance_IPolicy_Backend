package com.lti.services;

import java.util.List;

import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.Payment;
import com.lti.entity.User;
import com.lti.exception.UserNotFoundException;

public interface UserServices {

	public User addUser(User user);

	public User authenticateUser(String email, String password) throws UserNotFoundException;

	public Claim requestClaim(int policy_no, long contact_no, Claim clm);

	public MotorInsurance buyInsurance(String email, MotorInsurance insurance);

	public MotorInsurance renewInsurance(int policy_no, String email, long contact_no, String plan, int year,
			Payment payment);

	public double calculatePremium4Wheeler(String model, int age);

	public double calculatePremium2Wheeler(String model, int age);

	public User resetPassword(String email, String new_password) throws UserNotFoundException;

	public List<Claim> getClaimHistoryByEmail(String email);

	public List<MotorInsurance> getInsuranceByEmail(String email);

	public MotorInsurance getSingleInsuranceByPolicyNo(int policyNo);

}
