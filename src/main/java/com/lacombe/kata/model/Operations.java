package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.List;

public class Operations {
	public Operations() {
		this.operations = new ArrayList<Operation>();
	}
	public Operations(List<Operation> operations) {
		this.operations = operations;
	}

	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public int getBalance() {
		return operations.stream()
				.map(operation -> operation.getType().amountToApply(operation.getAmount()))
				.reduce(0, Integer::sum);
	}
}
