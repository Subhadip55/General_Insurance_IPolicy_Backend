package com.lti.services;

import java.util.List;

import com.lti.entity.Admin;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.User;
import com.lti.exception.AdminNotFoundException;

public interface AdminServices {

	public boolean loginAuthentication(Admin admin) throws AdminNotFoundException;

	public Claim approveClaim(int claimNo, int amount);

	public MotorInsurance approveInsurance(int policy_no);

	public List<Claim> getClaimList();

	public List<MotorInsurance> getInsuranceList();

	public Claim rejectClaim(int claimNo);

	public MotorInsurance rejectInsurance(int policy_no);

	public List<User> findAllUser();
}
