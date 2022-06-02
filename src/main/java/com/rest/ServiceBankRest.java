package com.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.entities.DepositTransfer;
import com.entities.Operation;
import com.entities.Withdrawal;
import com.service.IBankService;

@CrossOrigin(origins = "*") 
@RestController
@RequestMapping(value = "/services")
public class ServiceBankRest {

	@Autowired
	private IBankService bankService;
	private static Logger logger = Logger.getLogger(ServiceBankRest.class);
	
	
	
	
	// http://localhost:8080/services/deposit
	@RequestMapping(value = "/deposit/{id_user}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> deposit(@RequestBody DepositTransfer operation, @PathVariable Long id_user) {
		try {
			double amount = operation.getAmount();
			bankService.deposit(operation, id_user);
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	
	// http://localhost:8080/services/withdrawal
	@RequestMapping(value = "/withdrawal/{id_user}", method = RequestMethod.POST)
	public ResponseEntity<Boolean> withdrawal(@RequestBody Withdrawal operation, @PathVariable Long id_user) {
		try {

			
			
			boolean isOK = bankService.withdrawal(operation, id_user);
			if(!isOK) {
				return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/operations/{id_user}", method = RequestMethod.GET)
	public ResponseEntity<List<Operation>> listOperation(@PathVariable Long id_user){
		
		List<Operation> operations=null;
		try {
			operations = bankService.listOperation(id_user);
		} catch (Exception e) {
			return new ResponseEntity<List<Operation>>(operations, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Operation>>(operations, HttpStatus.OK);
		
	}
	
	
	
	
}
