package com.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_OP",discriminatorType=DiscriminatorType.STRING,length=1)
public abstract class Operation implements Serializable{

@Id
@SequenceGenerator(allocationSize=1, name = "MySeqOperation", sequenceName = "SeqOperation", initialValue = 1)
@GeneratedValue(generator = "MySeqOperation")
private int id;




@ManyToOne(fetch = FetchType.EAGER)
@JoinTable( 
        name = "operation_account", 
        joinColumns = @JoinColumn(name = "pk_operation", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(name = "numero_account", referencedColumnName = "numero")
) 
private Account account;

private Date dateOperation;
private double amount;
@Column(name = "type_op", insertable = false, updatable=false )
private String type_op;


public Operation() {
	super();
	
}


public Operation(Date dateOperation, double amount) {
	super();
	
	this.dateOperation = dateOperation;
	this.amount = amount;
}




public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}





public Account getAccount() {
	return account;
}


public void setAccount(Account account) {
	this.account = account;
}


public Date getDateOperation() {
	return dateOperation;
}


public void setDateOperation(Date dateOperation) {
	this.dateOperation = dateOperation;
}


public double getAmount() {
	return amount;
}


public void setAmount(double amount) {
	this.amount = amount;
}


public String getType_op() {
	return type_op;
}


public void setType_op(String type_op) {
	this.type_op = type_op;
}


	
	
	
	
}