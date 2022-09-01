package com.lti.Testservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lti.entity.Admin;
import com.lti.entity.Claim;
import com.lti.entity.MotorInsurance;
import com.lti.exception.AdminNotFoundException;
import com.lti.services.AdminServices;

@SpringBootTest
class AdminTestCases {

	@Autowired
	private AdminServices as;

	// test case for admin to login
	@Test
	@Disabled
	void adminLogin() {
		Admin adm = new Admin();
		adm.setAdmin_username("admin");
		adm.setAdmin_password("adminpassword");

		try {
			assertTrue(as.loginAuthentication(adm));
		} catch (AdminNotFoundException e) {
			e.printStackTrace();
		}
	}

	// test case for admin on  approving the insurance
	@Test
	@Disabled
	void testApproveInsurance() {

		MotorInsurance mi = as.approveInsurance(113);

		assertTrue(mi.isApproved());
		assertEquals(mi.getMessage(), "Approved");
	}

	// test case for admin on rejecting the insurance
	@Test
	@Disabled
	void testRejectInsurance() {

		MotorInsurance mi = as.rejectInsurance(1005);
		assertEquals(mi.getMessage(), "Rejected");
		assertFalse(mi.isApproved());
	}

	// test case for admin on approving the claim
	@Test
	@Disabled
	void testApproveClaim() {
		Claim clm = as.approveClaim(1009, 350);

		assertEquals(clm.getAmount(), 350);
		assertEquals(clm.getMessage(), "Approved");
	}

	// test case for admin on rejecting the claim
	@Test
	@Disabled
	void testRejectClaim() {

		Claim clm = as.rejectClaim(113);//reject only if it is not approved
		assertEquals(clm.getAmount(), 0.0);
		assertEquals(clm.getMessage(), "Rejected");
	}

}
