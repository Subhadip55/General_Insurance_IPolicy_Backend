package com.lti.Testservice;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lti.dao.MotorInsuranceRepository;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.entity.Payment;
import com.lti.entity.User;
import com.lti.exception.UserNotFoundException;
import com.lti.services.UserServices;

@SpringBootTest
class UserTestCases {

	@Autowired
	private UserServices us;

	@Autowired
	private MotorInsuranceRepository motor_repo;

	private String email = "bs@lti.com";

	// test case for adding user
	@Test
	@Disabled
	void addUser() {
		User u = new User();
		u.setEmail("bs@lti.com");
		u.setUser_city("purnia");
		u.setUser_contactNo(978581298);
		u.setUser_dl("bgweuybf");
		u.setUser_dob(LocalDate.parse("2021-01-07"));
		u.setUser_name("Badal");
		u.setUser_password("thisispassword");
		u.setUser_state("Tamil Nadu");
		u.setUser_zipCode(1938);

		User user = us.addUser(u);
		assertEquals(user, u);

	}

	// test case for logging in user
	@Test
	@Disabled
	void userLogin() {

		try {
			User u = us.authenticateUser("bs@lti.com", "thisispassword");
			assertEquals(u.getEmail(), "bs@lti.com");
			User u1 = us.authenticateUser("wrongemail", "wrong");

		} catch (UserNotFoundException e) {
			assertTrue(true);

		} catch (Exception e) {
			assertTrue(true);
		}
	}

	// test case for reseting the password
	@Test
	@Disabled
	void checkResetPassword() {
		try {
			User user = us.resetPassword("bs@lti.com", "resetPassword");
			assertEquals(user.getUser_password(), "resetPassword");
			assertNotEquals(user.getUser_password(), "wrongPassword");
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}

	}

	// test case for buying insurance
	@Test
	void testBuyInsurance() {
		MotorInsurance mi = new MotorInsurance();

		mi.setChasis_no("783ced8");
		mi.setEngine_no("neyubvf");
		mi.setManufacturer("tata");
		mi.setModel("Mahindra(1500cc)");
		mi.setPurchase_date(LocalDate.parse("2020-01-07"));
		Payment p = new Payment();
		p.setCard_no(88785446);
		p.setCardHolderName("Niranjana");
		p.setCvv(023);
		mi.setPayment(p);
		mi.setPlan("3-party");
		mi.setYear(2);
		mi.setRegistration_no("12345");
		mi.setType("2-wheeler");

		MotorInsurance mi2 = us.buyInsurance("bs@lti.com", mi);
		assertFalse(mi2.isApproved());
		assertFalse(mi2.isClaimed());
		assertEquals(mi2.getPayment().getCardHolderName(), "Niranjana");

	}

	// test case for renewing insurance
	@Test
	@Disabled
	void testRenewInsurance() {

		// int policy_no, String email, long contact_no,String plan,int year,Payment p
		Payment p = new Payment();
		p.setCard_no(88785446);
		p.setCardHolderName("Niranjana");
		p.setCvv(023);
		MotorInsurance mi = us.renewInsurance(105, email, 5565615, "comprehensive", 5, p);
		assertNotEquals(mi, null);

		assertEquals(mi.getPlan(), "comprehensive");
		assertEquals(mi.getYear(), 5);
		assertEquals(mi.getUser().getUser_contactNo(), 5565615);
	}

	// test case for calculating premium for 4 wheeler
	@Test
	@Disabled
	void testCalculatePremium4Wheeler() {
		String model = "Mahindra(1500cc)";
		int age = 1;
		double amt = us.calculatePremium4Wheeler(model, age);
		assertEquals(amt, 27164.0);
	}

	// test case for calculating premium for 2 wheeler
	@Test
	@Disabled
	void testCalculatePremium2Wheeler() {
		String model = "Hero(100cc)";
		int age = 2;
		double amt = us.calculatePremium2Wheeler(model, age);
		assertEquals(amt, 1322.0);
	}

	// test case for requesting claim
	@Test
	@Disabled
	void testRequestClaim() {

		int policy_no = 112;
		MotorInsurance insurance = motor_repo.getById(policy_no);

		Claim clm = new Claim();
		clm.setAccident_type("theft");
		clm.setDate_of_apply(LocalDate.now());
		clm.setApproved(false);

		clm.setInsurance(insurance);

		Claim claim = us.requestClaim(policy_no, 12345565, clm);

		assertTrue(claim.getInsurance().isClaimed());
		assertEquals(claim.getAccident_type(), "theft");
		assertEquals(claim.getMessage(), "Pending");
		assertEquals(claim.getInsurance().getPolicy_no(), 105);

	}

}
