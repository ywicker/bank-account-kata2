package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private String DEPOSIT_ERROR_MESSAGE = "Le depot doit etre d'un montant superieur a 0";
	private String WITHDRAWAL_ERROR_MESSAGE = "Le retrait doit etre d'un montant superieur a 0";

	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public Account() {
		this.operations = new ArrayList<Operation>();
	}


	public int getBalance() {
		int balance = 0;
		for(Operation operation : operations) {
			switch (operation.getType()) {
			case DEPOSIT:
				balance += operation.getAmount();
				break;
			case WITHDRAWAL:
				balance -= operation.getAmount();
				break;
			default:
				break;
			}
		}
		return balance;
	}

	public void deposit(final int amount) {
		assert amount > 0 : DEPOSIT_ERROR_MESSAGE;

		this.operations.add(new Operation(amount, OperationType.DEPOSIT));
	}

	public void withdrawal(final int amount) {
		assert amount > 0 : WITHDRAWAL_ERROR_MESSAGE;

		this.operations.add(new Operation(amount, OperationType.WITHDRAWAL));
	}
	
}
