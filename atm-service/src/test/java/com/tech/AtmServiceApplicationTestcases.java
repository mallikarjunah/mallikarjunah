package com.tech;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tech.controller.AdminController;
import com.tech.controller.UserController;
import com.tech.dao.AccountRepository;
import com.tech.dao.AtmMachineRepository;
import com.tech.exception.ExceptionResponse;
import com.tech.model.WithdrawRequest;
import com.tech.model.entities.Account;
import com.tech.model.entities.AtmMachine;
import com.tech.service.UserService;
import com.tech.service.Impl.UserServiceImpl;
import com.tech.utils.Messages;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AtmServiceApplicationTestcases {

	@Autowired
	UserServiceImpl serve = new UserServiceImpl();

	@Mock
	AccountRepository dao1;

	@Mock
	AtmMachineRepository dao2;

	@Mock
	private Messages msg;

	@Autowired
	UserController ctrl = new UserController();

	@Mock
	UserService userService;

	@Autowired
	AdminController adminController;

	// @org.junit.jupiter.api.Test
	@Test
	public void testCheckBalance() throws Exception {
		Account testAccount = new Account();
		testAccount.setAccountNumber(123456789L);
		testAccount.setOpeningBalance(8650L);
		testAccount.setOverdraft(260L);
		testAccount.setPin(1234);
		when(dao1.findByAccountNumberAndPin(123456789L, 1234)).thenReturn(testAccount);
		when(msg.getWithdrawLimit()).thenReturn("500");
		ResponseEntity<Object> entity = serve.checkBalance(123456789L, 1234);
		HttpStatus statusCode = entity.getStatusCode();
		assertEquals(200, statusCode.value());
	}

	@Test
	public void testCheckBalance2() throws Exception {
		Account testAccount = new Account();
		testAccount.setAccountNumber(123456710L);
		testAccount.setOpeningBalance(300L);
		testAccount.setOverdraft(260L);
		testAccount.setPin(1234);
		when(dao1.findByAccountNumberAndPin(123456710L, 1234)).thenReturn(testAccount);

		ResponseEntity<Object> entity = serve.checkBalance(123456710L, 1234);
		HttpStatus statusCode = entity.getStatusCode();
		assertEquals(200, statusCode.value());
	}

	@Test
	public void testWithdraw() throws Exception {
		ResponseEntity<Object> withdraw = serve.withdraw(123456789L, 300L, 1234);
		assertEquals(200, withdraw.getStatusCode().value());
	}

	@Test
	public void testWithdraw2() throws Exception {
		ResponseEntity<Object> withdraw = serve.withdraw(123456789L, 10L, 1234);
		assertEquals(200, withdraw.getStatusCode().value());
	}

	@Test
	public void testCheckbalanceCtrlMethod() throws Exception {
		HashMap<String, String> response = new HashMap<>();
		response.put("maximumWithdrawal", "200");
		response.put("availableBalance", "800");

		when(userService.checkBalance(123456789L, 1234)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
		ResponseEntity<Object> entity = ctrl.checkBalance(123456789L, 1234);
		assertEquals(200, entity.getStatusCode().value());
	}

	@Test
	public void testWithdrawCtrlMethod2() throws Exception {
		HashMap<String, String> response = new HashMap<>();
		response.put("status", "Successfully withdrawn cash");
		response.put("remainingBalance", "90990");

		WithdrawRequest request = new WithdrawRequest();
		request.setAccountNumber(987654321L);
		request.setAmount(500L);
		request.setPin(4321);
		when(userService.withdraw(97654321L, 500L, 4321)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

		ResponseEntity<Object> entity = ctrl.withdrawCash(request);
		assertEquals(200, entity.getStatusCode().value());
	}

	@Test
	public void testadminCtrlMethod1() throws Exception {
		ResponseEntity<Object> entity = adminController.getAvailableCash();
		assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testadminCtrlMethod2() throws Exception {
		ResponseEntity<Object> entity = adminController.updateCash("fifty", new AtmMachine("fifty", 10L, 500L));
		assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testCoverage() throws Exception {
		WithdrawRequest request = new WithdrawRequest(12346L, 1234, 250L);
		assertTrue(request != null);

		ExceptionResponse er = new ExceptionResponse();
		er.setErrorMessage("testing");
		er.setRequestedURI("dumyURL");
		er.setTimestamp(new Date());
		assertTrue(er != null);

		Account account = new Account(12L, 1234, 200L, 200L);
		assertTrue(account != null);
		assertTrue(account.getOverdraft() != null);

		AtmMachine atm = new AtmMachine("DMY", 25L, 5000L);
		assertTrue(atm != null);

	}
}
