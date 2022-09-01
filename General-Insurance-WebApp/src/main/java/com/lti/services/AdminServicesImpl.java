package com.lti.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dao.AdminRepository;
import com.lti.dao.ClaimRepository;
import com.lti.dao.MotorInsuranceRepository;
import com.lti.dao.UserRepository;
import com.lti.entity.Admin;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.User;
import com.lti.exception.AdminNotFoundException;

@Service("adminservice")
public class AdminServicesImpl implements AdminServices {

	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private ClaimRepository claimRepo;
	@Autowired
	private MotorInsuranceRepository motorRepo;
	@Autowired
	private UserRepository user_repo;

	// service to get all users
	@Override
	public List<User> findAllUser() {
		List<User> uList = user_repo.findAll();
		return uList;
	}

	// service for admin to login
	@Override
	public boolean loginAuthentication(Admin admin) throws AdminNotFoundException {

		Admin ad = adminRepo.getById(admin.getAdmin_username());

		if (ad != null) {
			if ((ad.getAdmin_password()).equals(admin.getAdmin_password())) {
				return true;
			}
			return false;
		} else {
			throw new AdminNotFoundException("No Admin was Found with given credintials");
		}

	}

	// service for admin to approve the insurance
	@Override
	@Transactional
	public MotorInsurance approveInsurance(int policy_no) {
		// TODO Auto-generated method stub
		MotorInsurance mi = motorRepo.getById(policy_no);
		mi.setApproved(true);
		mi.setDateOfExpiry(LocalDate.now().plusYears(mi.getYear()));
		mi.setMessage("Approved");
		return motorRepo.save(mi);
	}

	// service for admin to rejecting the claim
	@Override
	@Transactional
	public MotorInsurance rejectInsurance(int policyNo) {
		// TODO Auto-generated method stub
		MotorInsurance mi = motorRepo.getById(policyNo);
		if (mi.getMessage().equals("Pending Approval")) {
			mi.setMessage("Rejected");
			motorRepo.save(mi);
			return mi;
		}
		return null;
	}

	// service for admin to approve the claim
	@Override
	@Transactional
	public Claim approveClaim(int claimNo, int amount) {

		Claim c = claimRepo.getById(claimNo);
		c.setApproved(true);
		c.setAmount(amount);
		c.setMessage("Approved");

		claimRepo.save(c);
		return c;
	}

	// this method is for admin to reject the claim
	@Override
	@Transactional
	public Claim rejectClaim(int claimNo) {

		Claim c = claimRepo.getById(claimNo);
		if (c.getMessage().equals("Pending")) {
			c.setApproved(false);
			c.setMessage("Rejected");
			c.getInsurance().setApproved(false);
			c.setAmount(0);
			c.getInsurance().setMessage("Rejected");
			claimRepo.save(c);
			return c;
		}
		return null;
	}

	// service for admin to see all claim list
	@Override
	public List<Claim> getClaimList() {
		// TODO Auto-generated method stub
		return claimRepo.findAll();
	}

	// service for admin to see all insurance list
	@Override
	public List<MotorInsurance> getInsuranceList() {
		// TODO Auto-generated method stub
		return motorRepo.findAll();
	}

}
