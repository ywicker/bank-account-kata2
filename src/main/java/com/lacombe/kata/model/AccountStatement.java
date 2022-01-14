package com.lacombe.kata.model;

import java.util.Date;
import java.util.List;

public class AccountStatement {
	public AccountStatement(Date startDate, Date endDate, int oldBalance, int newBalance, List<Operation> operations) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.oldBalance = oldBalance;
		this.newBalance = newBalance;
		this.operations = operations;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public int getOldBalance() {
		return oldBalance;
	}
	public int getNewBalance() {
		return newBalance;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	private Date startDate;
	private Date endDate;
	private int oldBalance;
	private int newBalance;
	private List<Operation> operations;
}
