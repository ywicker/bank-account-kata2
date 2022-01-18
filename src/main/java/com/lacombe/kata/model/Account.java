package com.lacombe.kata.model;

import java.util.Date;
import java.util.List;

public class Account {
	private Operations operations;
	private DateProvider dateProvider;

	public List<Operation> getOperations() {
		return operations.getOperations();
	}

	public Account(final DateProvider dateProvider) {
		this.dateProvider = dateProvider;
		this.operations = new Operations();
	}

	public int getBalance() {
		return operations.getBalance();
	}

	public void deposit(final Amount amount) {
		this.operations.addOperation(new Operation(this.dateProvider.getDate(), amount, OperationType.DEPOSIT));
	}

	public void withdrawal(final Amount amount) {
		this.operations.addOperation(new Operation(this.dateProvider.getDate(), amount, OperationType.WITHDRAWAL));
	}

	public AccountStatement getAccountStatement(final Date startDate, final Date endDate) {
		final List<Operation> operationList = operations.getOperations(startDate, endDate);
		final int oldBalance = operations.getBalance(new Date(0), startDate);
		final int newBalance = operations.getBalance(new Date(0), endDate);
		return new AccountStatement(startDate, endDate, oldBalance, newBalance, operationList);
	}
}
