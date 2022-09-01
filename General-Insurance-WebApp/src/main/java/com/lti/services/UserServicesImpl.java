package com.lti.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dao.ClaimRepository;
import com.lti.dao.MotorInsuranceRepository;
import com.lti.dao.UserRepository;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.Payment;
import com.lti.entity.User;
import com.lti.exception.UserNotFoundException;

@Service("user_service")
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepository user_repo;
	@Autowired
	private MotorInsuranceRepository motor_repo;
	@Autowired
	private ClaimRepository claim_repo;

	// service for user to create an account
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public User addUser(User user) {
		user_repo.save(user);
		return user;
	}

	// service for user to login
	@Override
	public User authenticateUser(String email, String password) throws UserNotFoundException {

		Optional<User> findUser = user_repo.findById(email);
		User u = null;
		if (findUser.isPresent()) {
			u = findUser.get();

			// checking whether the password is correct or not
			if (u.getUser_password().equals(password)) {
				return u;
			}
		} else {
			throw new UserNotFoundException("Email is incorrect");
		}
		return null;
	}

	// service for user to reset password for his account
	@Override
	@Transactional
	public User resetPassword(String email, String new_password) throws UserNotFoundException {

		Optional<User> findUser = user_repo.findById(email);
		User u = null;
		if (findUser.isPresent()) {
			u = findUser.get();
			u.setUser_password(new_password);
			user_repo.save(u);
		} else {
			throw new UserNotFoundException("User Not Found");
		}

		return u;

	}

	// service for user to buy a insurance
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MotorInsurance buyInsurance(String email, MotorInsurance insurance) {

		List<MotorInsurance> allMiTable = motor_repo.findAll();
		for (MotorInsurance mi : allMiTable) {
			if (mi.getUser().getEmail().equals(email)) {
				if (mi.getRegistration_no().equals(insurance.getRegistration_no())) {
					return null;
				}
			}
		}

		Optional<User> findUser = Optional.ofNullable(user_repo.getById(email));
		User u = findUser.get();
		insurance.setUser(u);
		motor_repo.save(insurance);
		return insurance;
	}

	// service for user to renew insurance
	@Override
	@Transactional
	public MotorInsurance renewInsurance(int policy_no, String email, long contact_no, String plan, int year,
			Payment p) {
		MotorInsurance mi = motor_repo.getById(policy_no);
		mi.setYear(year);
		mi.setPlan(plan);
		mi.setDateOfExpiry(mi.getDateOfExpiry().plusYears(year));
		mi.getUser().setUser_contactNo(contact_no);
		mi.setPayment(p);
		motor_repo.save(mi);
		return mi;
	}

	// service for user to request insurance claim
	@Override
	@Transactional
	public Claim requestClaim(int policy_no, long contact_no, Claim clm) {

		MotorInsurance insurance = motor_repo.getById(policy_no);

		if (insurance.getMessage().equals("Pending Approval") || insurance.getMessage().equals("Rejected")) {
		} else {
			clm.setDate_of_apply(LocalDate.now());
			clm.setApproved(false);
			insurance.setClaimed(true);
			clm.setInsurance(insurance);

			claim_repo.save(clm);
			return clm;
		}
		return null;

	}

	// method to calculate insurance premium for 4-wheeler
	public double calculate_amt_4wheeler(double default_amt, int age) {

		if (age < 5) {
			return default_amt + 500 * age;
		} else if (age >= 5 && age <= 10) {
			return default_amt + 600 * age;
		} else if (age > 10 && age < 20) {
			return default_amt + 730 * age;
		} else {
			return default_amt + 800 * age;
		}
	}

	// method to calculate insurance premium for 2-wheeler
	public double calculate_amt_2wheeler(double default_amt, int age) {

		if (age < 5) {
			return default_amt + 100 * age;
		} else if (age >= 5 && age <= 10) {
			return default_amt + 200 * age;
		} else if (age > 10 && age < 20) {
			return default_amt + 285 * age;
		} else {
			return default_amt + 350 * age;
		}
	}

	// service for user to calculate insurance premium for 4-Wheeler
	@Override
	public double calculatePremium4Wheeler(String model, int age) {
		// TODO Auto-generated method stub

		double default_amt = 0.0;

		if (model.equals("Maruti Suzuki(1000cc)")) {
			default_amt = 12398;
			return calculate_amt_4wheeler(default_amt, age);

		} else if (model.equals("Tata(1100cc)")) {

			default_amt = 25369;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Mahindra(1500cc)")) {
			default_amt = 26664;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Kia(1800cc)")) {
			default_amt = 27023;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Toyota(2000cc)")) {
			default_amt = 30004;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Audi(2500cc)")) {
			default_amt = 42301;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Nissan(800cc)")) {
			default_amt = 7998;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Renault(900cc)")) {
			default_amt = 8569;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("BMW(3000cc)")) {
			default_amt = 45682;
			return calculate_amt_4wheeler(default_amt, age);
		} else if (model.equals("Rolls Royce(3500cc)")) {
			default_amt = 95732;
			return calculate_amt_4wheeler(default_amt, age);
		}

		return 0.0;
	}

	// service for user to calculate insurance premium for 2-Wheeler
	@Override
	public double calculatePremium2Wheeler(String model, int age) {
		// TODO Auto-generated method stub
		double default_amt = 0.0;
		if (model.equals("Hero(75cc)")) {
			default_amt = 714;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Hero(100cc)")) {
			default_amt = 1122;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Bajaj(180cc)")) {
			default_amt = 1366;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("TVS(200cc)")) {
			default_amt = 1402;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Royal Enfield(350cc)")) {
			default_amt = 2804;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("KTM(390cc)")) {
			default_amt = 3008;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Kawasaki(600cc)")) {
			default_amt = 7998;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Yamaha(400cc)")) {
			default_amt = 4569;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Ducati(1000cc)")) {
			default_amt = 9889;
			return calculate_amt_2wheeler(default_amt, age);
		} else if (model.equals("Ola_Electric")) {
			default_amt = 650;
			return calculate_amt_2wheeler(default_amt, age);
		} else {
			return 0.0;
		}

	}

	// service for user to get list of bought insurance
	@Override
	public List<MotorInsurance> getInsuranceByEmail(String email) {

		List<MotorInsurance> allinsurance = motor_repo.findAll();
		List<MotorInsurance> req_ins = new ArrayList<MotorInsurance>();
		for (MotorInsurance mi : allinsurance) {

			if (mi.getUser().getEmail().equals(email)) {
				req_ins.add(mi);
			}

		}
		return req_ins;
	}

	// service for user to get claim history to check whether it is approved or not
	@Override
	public List<Claim> getClaimHistoryByEmail(String email) {

		List<Claim> allclaim = claim_repo.findAll();
		List<Claim> req_claim = new ArrayList<Claim>();
		for (Claim c : allclaim) {

			if (c.getInsurance().getUser().getEmail().equals(email)) {
				req_claim.add(c);
			}

		}
		return req_claim;
	}

	// method to get insurance details from policy no
	@Override
	public MotorInsurance getSingleInsuranceByPolicyNo(int policyNo) {
		Optional<MotorInsurance> mi = motor_repo.findById(policyNo);
		MotorInsurance ins = null;
		if (mi.isPresent()) {
			ins = mi.get();
			return ins;
		}
		return ins;
	}

}
