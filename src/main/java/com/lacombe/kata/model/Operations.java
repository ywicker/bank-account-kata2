package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

	/**
	 * Get operations between startDate included and endDate excluded
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Operation> getOperations(final Date startDate, final Date endDate) {
		return operations.stream()
				.filter(operation -> (operation.getDate().after(startDate) || operation.getDate().equals(startDate)) 
						&& operation.getDate().before(endDate))
				.collect(Collectors.toList());
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
	}

	public Date getFirstOperationDate(final Date startDate, final Date endDate) {
		return operations.stream().map(Operation::getDate).min(Date::compareTo).get();
	}

	public int getBalance(List<Operation> operations) {
		return operations.stream()
				.map(operation -> operation.getType().amountToApply(operation.getAmount()))
				.reduce(0, Integer::sum);
	}
	public int getBalance() {
		return getBalance(operations);
	}
	public int getBalance(final Date startDate, final Date endDate) {
		return getBalance(getOperations(startDate, endDate));
	}
}
