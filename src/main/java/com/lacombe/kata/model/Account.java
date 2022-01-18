package com.lacombe.kata.model;

import java.util.Date;
import java.util.List;

public class Account {
	private String DEPOSIT_ERROR_MESSAGE = "Le depot doit etre d'un montant superieur a 0";
	private String WITHDRAWAL_ERROR_MESSAGE = "Le retrait doit etre d'un montant superieur a 0";

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

	public void deposit(final int amount) {
		assert amount > 0 : DEPOSIT_ERROR_MESSAGE;

		this.operations.addOperation(new Operation(this.dateProvider.getDate(), amount, OperationType.DEPOSIT));
	}

	public void withdrawal(final int amount) {
		assert amount > 0 : WITHDRAWAL_ERROR_MESSAGE;

		this.operations.addOperation(new Operation(this.dateProvider.getDate(), amount, OperationType.WITHDRAWAL));
	}

	public AccountStatement getAccountStatement(final Date startDate, final Date endDate) {
		final List<Operation> operationList = operations.getOperations(startDate, endDate);
		final int oldBalance = operations.getBalance(new Date(0), startDate);
		final int newBalance = operations.getBalance(new Date(0), endDate);
		return new AccountStatement(startDate, endDate, oldBalance, newBalance, operationList);
	}
}
