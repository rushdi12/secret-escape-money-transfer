package com.secret.escape.account.transfer.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity 
public class Account implements java.io.Serializable{

 
	public Account() {
		
	}
	
	public Account(Long id, String accountNumber, String accountHolderName, String email, Long balance,
			Set<Transaction> transactions) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.email = email;
		this.balance = balance;
		this.transactions = transactions;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 165650711006352410L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "accountNumber", unique=true, length = 20)
	private String accountNumber;

	@Column(name = "accountHolderName", length = 20)
	private String accountHolderName;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "balance")
	private Long balance;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Transaction> transactions = new HashSet<Transaction>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

}
