package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.Account;
import com.entities.Operation;


public interface IAccountRepository extends JpaRepository<Account, Long>{
	
	@Query(" From Account a where a.numero = :numero")
	public Account getAccount(@Param("numero")Long numero);
	
}