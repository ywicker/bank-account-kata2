package com.lacombe.kata.model;

public enum OperationType {
	DEPOSIT("Depot"){
		public int amountToApply(final Amount amount) {
			return amount.getValue();
		}
	},
	WITHDRAWAL("Retrait") {
		public int amountToApply(final Amount amount) {
			return -amount.getValue();
		}
	};

	private final String label;

	public String getLabel() {
		return label;
	}

	private OperationType(String label) {
		this.label = label;
	}

	public int amountToApply(final Amount amount) {
		return amount.getValue();
	}
}
