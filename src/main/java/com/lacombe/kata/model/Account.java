package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	private String DEPOSIT_ERROR_MESSAGE = "Le depot doit etre d'un montant superieur a 0";
	private String WITHDRAWAL_ERROR_MESSAGE = "Le retrait doit etre d'un montant superieur a 0";

	private Operations operations;

	public List<Operation> getOperations() {
		return operations.getOperations();
	}

	public Account() {
		this.operations = new Operations();
	}

	public int getBalance() {
		return operations.getBalance();
	}

	public void deposit(final int amount) {
		assert amount > 0 : DEPOSIT_ERROR_MESSAGE;

		this.operations.addOperation(new Operation(amount, OperationType.DEPOSIT));
	}

	public void withdrawal(final int amount) {
		assert amount > 0 : WITHDRAWAL_ERROR_MESSAGE;

		this.operations.addOperation(new Operation(amount, OperationType.WITHDRAWAL));
	}

	public AccountStatement getAccountStatement(final Date startDate, final Date endDate) {
		
		return new AccountStatement(startDate, endDate, 0, 0, operations.getOperations());
	}

	public void setOperations(Operations operations) {
		this.operations = operations;
	}
}
