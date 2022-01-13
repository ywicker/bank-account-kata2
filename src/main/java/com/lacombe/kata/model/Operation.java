package com.lacombe.kata.model;

import java.util.Date;

public class Operation {
	public Operation(final int amount, final OperationType type) {
		this.date = new Date();
		this.amount = amount;
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public OperationType getType() {
		return type;
	}
	public void setType(OperationType type) {
		this.type = type;
	}
	private Date date;
	private int amount;
	private OperationType type;
}
