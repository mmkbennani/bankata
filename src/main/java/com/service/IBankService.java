package com.service;

import java.util.List;

import com.entities.DepositTransfer;
import com.entities.Operation;
import com.entities.Withdrawal;

public interface IBankService {

	public void deposit(DepositTransfer v, Long id_user);
	public boolean withdrawal(Withdrawal operation, Long id_user);
	public List<Operation> listOperation(Long id_user);
}