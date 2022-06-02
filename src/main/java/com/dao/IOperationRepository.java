package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.Operation;

public interface IOperationRepository extends JpaRepository<Operation, Integer>{
	
	@Query(" From Operation o where o.account.numero = :id_user order by o.dateOperation desc")
	public List<Operation> listOperation(Long id_user);
	
	@Modifying
	@Query("delete from Operation")
	public void deleteOperation();
	
}