package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public Account() {
		this.operations = new ArrayList<Operation>();
	}


	public int getBalance() {
		return 0;
	}

	public void deposit(final int amount) {
	}

	public void withdrawal(final int amount) {
	}
}
