package com.lacombe.kata.model;

public class Amount {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		Amount other = (Amount) obj;
		if (value != other.value)
			return false;
		return true;
	}

	public int getValue() {
		return value;
	}

	private Amount(int value) {
		super();
		this.value = value;
	}

	private int value;

	public static Amount createAmount(final int value) {
		assert Integer.signum(value) == 1;
		return new Amount(value);
	}
}
