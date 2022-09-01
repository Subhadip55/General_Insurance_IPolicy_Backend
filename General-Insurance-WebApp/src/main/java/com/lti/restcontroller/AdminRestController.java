package com.lti.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.entity.Admin;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.User;
import com.lti.exception.AdminNotFoundException;
import com.lti.services.AdminServices;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminRestController {

	@Autowired
	private AdminServices admin_services;

	//method to get all user
	@GetMapping("/allusers")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> uList = new ArrayList<User>();
			uList = admin_services.findAllUser();
			if (uList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(uList, HttpStatus.OK);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	//method for admin to login
	@PostMapping("/login")
	public boolean authenticate(@RequestBody Admin admin) {
		try {
			return admin_services.loginAuthentication(admin);
		} catch (AdminNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	//method for admin to approve the insurance 
	@GetMapping("/approve_insurance/{pcy}")
	public MotorInsurance approve_Insurance(@PathVariable("pcy") int policy_no) {
		return admin_services.approveInsurance(policy_no);
	}

	//method for admin to reject the insurance
	@GetMapping("/rejectInsurance/{plno}")
	public MotorInsurance rejectInsurance(@PathVariable("plno") int policy_no) {
		return admin_services.rejectInsurance(policy_no);

	}

	//method for admin to approve the claim
	@GetMapping("/approve_claim/{clm}/{amt}")
	public Claim approveClaim(@PathVariable("clm") int claim_no, @PathVariable("amt") int amount) {
		return admin_services.approveClaim(claim_no, amount);
	}

	// method for admin to reject the claim
	@GetMapping("/rejectClaim/{clmno}")
	public Claim rejectClaim(@PathVariable("clmno") int claim_no) {
		return admin_services.rejectClaim(claim_no);

	}

	//method for viewing the claim history 
	@GetMapping("/dashboard/showClaim") 
	public List<Claim> get_ClaimList() {
		return admin_services.getClaimList();
	}

	//method for viewing the insurance details
	@GetMapping("/dashboard/showInsurance") // for viewing
	public List<MotorInsurance> get_InsuranceList() {
		return admin_services.getInsuranceList();
	}
}
