package com.lti.restcontroller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.Payment;
import com.lti.entity.User;
import com.lti.entity.UserLogin;
import com.lti.exception.UserNotFoundException;
import com.lti.services.UserServices;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userapp")
public class UserRestController {

	@Autowired
	private UserServices uService;

	// method for creating user
	@PostMapping("/users")
	public ResponseEntity<User> createOrsaveUser(@RequestBody User newUser) {
		try {
			User u = uService.addUser(newUser);
			return new ResponseEntity<>(u, HttpStatus.CREATED);
		} catch (Exception e) {
			//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return null;
		}
	}

	// method for user login
	@PostMapping("/login")
	public ResponseEntity<User> loginAuthentication(@RequestBody UserLogin ul) {
		User user;
		String email = ul.getEmail();
		String psw = ul.getUser_password();
		try {
			user = uService.authenticateUser(email, psw);

			if (user != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				//return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
				return null;
			}
		} catch (UserNotFoundException e) {
			// e.printStackTrace();
			//return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			return null;
		} 
			
	}

	// method for reseting password
	@PostMapping("/reset_password")
	public ResponseEntity<User> resetPassword(@RequestBody UserLogin ul) {
		try {
			String email = ul.getEmail();
			String newPsw = ul.getUser_password();
			User u = uService.resetPassword(email, newPsw);
			return new ResponseEntity<>(u, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
	}

	// method for buying password
	@PostMapping("/buy_insurance/{email}")
	public ResponseEntity<MotorInsurance> selectBuyInsurance(@PathVariable("email") String email,
			@RequestBody MotorInsurance mi) {

		MotorInsurance ins = uService.buyInsurance(email, mi);

		if (ins == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(ins, HttpStatus.OK);
	}

	// method for renewing Insurance
	@PutMapping("/renewInsurance/{pn}/{eml}/{ctn}/{pl}/{yr}")
	public MotorInsurance renew_Insurance(@PathVariable("pn") int policy_no, @PathVariable("eml") String email,
			@PathVariable("ctn") long contact_no, @PathVariable("pl") String plan, @PathVariable("yr") int year,
			@RequestBody Payment payment) {

		return uService.renewInsurance(policy_no, email, contact_no, plan, year, payment);
	}

	// method for calculating insurance premium for 4 wheeler using model and age of
	// the vehicle
	@GetMapping("/calculateInsuranceEstimate/4-wheeler/{model}/{age}")
	public double calculateInsuranceEstimate4wheeler(@PathVariable("model") String model,
			@PathVariable("age") int age) {
		return uService.calculatePremium4Wheeler(model, age);
	}

	// method for calculating insurance premium for 2 wheeler using model and age of
	// the vehicle
	@GetMapping("/calculateInsuranceEstimate/2-wheeler/{model}/{age}")
	public double calculateInsuranceEstimate2wheeler(@PathVariable("model") String model,
			@PathVariable("age") int age) {
		return uService.calculatePremium2Wheeler(model, age);
	}

	// method for getting list of all claims made by user
	@GetMapping("/claimHistory/{eml}")
	public List<Claim> getClaimHistoryByEmail(@PathVariable("eml") String email) {
		return uService.getClaimHistoryByEmail(email);
	}

	// method for getting list of all insurance made by user
	@GetMapping("/insuranceDetails/{eml}")
	public List<MotorInsurance> getInsuranceByEmail(@PathVariable("eml") String email) {
		return uService.getInsuranceByEmail(email);
	}

	// method for user to request insurance claim
	@PostMapping("/claim/{pn}/{cnt}")
	public Claim requestClaim(@PathVariable("pn") int policy_no, @PathVariable("cnt") long contact_no,
			@RequestBody Claim claim) {
		return uService.requestClaim(policy_no, contact_no, claim);
	}

	// method to get insurance details by policy number
	@GetMapping("/insurance/{pln}")
	public MotorInsurance getInsuranceByPolicyNo(@PathVariable("pln") int policyNo) {
		return uService.getSingleInsuranceByPolicyNo(policyNo);
	}
}
