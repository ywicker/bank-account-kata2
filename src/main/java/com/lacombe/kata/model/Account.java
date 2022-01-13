package com.lacombe.kata.model;

import java.util.Collections;
import java.util.List;

public class Account {
	private int balance;
	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public int getBalance() {
		return balance;
	}

	public Account() {
		this.balance = 0;
		this.operations = Collections.emptyList();
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
}
