package com.lacombe.kata.model;

public enum OperationType {
	DEPOSIT("Depot"),
	WITHDRAWAL("Retrait") {
		public int amountToApply(final int amount) {
			return - amount;
		}
	};

	public String getLabel() {
		return label;
	}

	private final String label;

	private OperationType(String label) {
		this.label = label;
	}

	public int amountToApply(final int amount) {
		return amount;
	}
}
