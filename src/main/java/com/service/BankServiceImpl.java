package com.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IAccountRepository;
import com.dao.IOperationRepository;
import com.entities.Account;
import com.entities.DepositTransfer;
import com.entities.Operation;
import com.entities.Withdrawal;

@Service
@Transactional
public class BankServiceImpl implements IBankService{

	@Autowired
	private IOperationRepository operationRepository;
	@Autowired
	private IAccountRepository accountRepository;
	

	private static Logger logger = Logger.getLogger(BankServiceImpl.class);
	

	@Override
	public void deposit(DepositTransfer v, Long id_user) {
		Account account = accountRepository.getAccount(id_user);
		v.setAccount(account);
		account.setAmount(account.getAmount()+ v.getAmount());
		operationRepository.save(v);
		accountRepository.save(account);
	}

	@Override
	public boolean withdrawal(Withdrawal operation, Long id_user) {
		Account account = accountRepository.getAccount(id_user);
		
		if(account.getAmount() - operation.getAmount()>=0) {
			operation.setAccount(account);
			account.setAmount(account.getAmount() - operation.getAmount()>0?account.getAmount() - operation.getAmount():0);
			operationRepository.save(operation);
			accountRepository.save(account);
			return true;
		}
		
		
		return false;
	}

	@Override
	public List<Operation> listOperation(Long id_user) {
		
		return operationRepository.listOperation(id_user);
	}

}