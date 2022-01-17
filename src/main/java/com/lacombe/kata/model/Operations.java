package com.lacombe.kata.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Operations {
	private String DATE_ORDER_ERROR_MESSAGE = "La date de debut ne peut pas etre plus recente que la date de fin";
	private String DATE_1900_ERROR_MESSAGE = "Les dates anterieures a 1900 ne sont pas gerees";

	public Operations() {
		this.operations = new ArrayList<>();
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
	@SuppressWarnings("deprecation")
	public List<Operation> getOperations(final Date startDate, final Date endDate) {
		assert startDate.before(endDate) : DATE_ORDER_ERROR_MESSAGE;
		assert startDate.getYear() >= 0 : DATE_1900_ERROR_MESSAGE;

		return operations.stream()
				.filter(operation -> (operation.getDate().after(startDate) || operation.getDate().equals(startDate)) 
						&& operation.getDate().before(endDate))
				.collect(Collectors.toList());
	}

	public void addOperation(Operation operation) {
		this.operations.add(operation);
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
