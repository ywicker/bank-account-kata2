package com.lacombe.kata.model;

import java.util.Date;

public class Operation {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public Operation(final Date date, final Amount amount, final OperationType type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public Amount getAmount() {
		return amount;
	}
	public OperationType getType() {
		return type;
	}
	private Date date;
	private Amount amount;
	private OperationType type;
}
