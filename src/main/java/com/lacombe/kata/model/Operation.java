package com.lacombe.kata.model;

import java.util.Date;

public class Operation {
	public Operation(final Date date, final int amount, final OperationType type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
	public Operation(final int amount, final OperationType type) {
		this(new Date(), amount, type);
	}
	public Date getDate() {
		return date;
	}
	public int getAmount() {
		return amount;
	}
	public OperationType getType() {
		return type;
	}
	private Date date;
	private int amount;
	private OperationType type;
}
