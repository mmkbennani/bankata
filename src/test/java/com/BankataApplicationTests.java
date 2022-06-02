package com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.entities.DepositTransfer;
import com.entities.Operation;
import com.entities.Withdrawal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.IBankService;

@RunWith(SpringRunner.class)
@SpringBootTest
class BankataApplicationTests {
	private static Logger logger = Logger.getLogger(BankataApplicationTests.class);
	@Autowired
    private WebApplicationContext context;
	static long id_account = 7737;
	
	
	@Autowired
	private IBankService bankService;
	@Autowired
	private ObjectMapper objectMapper;
	
	MockMvc mockMvc;



    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).dispatchOptions(true).build();
    }
	
	@Test
	public void testDeposit() {

		// deposit
		double amount = 200;
		DepositTransfer depositTransfer = new  DepositTransfer(new Date(), amount);
		bankService.deposit(depositTransfer,id_account);
		//select operations
		List<Operation> list = bankService.listOperation(id_account);
		assertTrue(list.size() == 1);
		Operation op = list.get(0);
		assertTrue(op.getAmount() == amount);
		assertTrue(op.getType_op().equals("D"));
		

	}

	@Test
	public void testWithdrawal() {
		// withdrawal
		double amount = 100;
		Withdrawal withdrawal = new Withdrawal(new Date(), amount);
		bankService.withdrawal(withdrawal, id_account);
		//select operations
		List<Operation> list = bankService.listOperation(id_account);
		assertTrue(list.size() == 1);
		Operation op = list.get(0);
		assertTrue(op.getAmount() == amount);
		assertTrue(op.getType_op().equals("W"));

	}

	@Test
	public void testListOperation() {
		
		DepositTransfer depositTransfer = new  DepositTransfer(new Date(), 5000);
		Withdrawal withdrawal1 = new Withdrawal(new Date(), 500);
		Withdrawal withdrawal2 = new Withdrawal(new Date(), 500);
		bankService.deposit(depositTransfer, id_account);
		bankService.withdrawal(withdrawal1, id_account);
		bankService.withdrawal(withdrawal2, id_account);
		//list all operations
		List<Operation> list = bankService.listOperation(id_account);
		assertEquals(15, list.size());

	}
	
	
	@Test
    public void shouldReturnIsNotAcceptable() throws Exception {
		Withdrawal withdrawal = new Withdrawal(new Date(), 5000);
        mockMvc.perform(
        		post("/services/withdrawal/"+id_account).content(objectMapper.writeValueAsString(withdrawal))
                )
        		.andExpect(status().isNotAcceptable());
    }
	

}
