package com.lacombe.kata.model;

import java.util.*;
import java.util.stream.Collectors;

public class Operations {
	private List<Operation> operations;

	public Operations() {
		this.operations = new ArrayList<>();
	}

	public List<Operation> getOperations() {
		return List.copyOf(operations);
	}

	public List<Operation> getOperations(final Date startDate, final Date endDate) {
		return operations.stream()
				.filter(operation -> (operation.getDate().after(startDate) || operation.getDate().equals(startDate)) 
						&& operation.getDate().before(endDate))
				.collect(Collectors.toList());
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
