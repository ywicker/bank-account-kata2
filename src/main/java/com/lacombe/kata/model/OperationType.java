package com.lacombe.kata.model;

public enum OperationType {
	DEPOSIT() {
		public int amountToApply(final Amount amount) {
			return amount.getValue();
		}
	},
	WITHDRAWAL() {
		public int amountToApply(final Amount amount) {
			return -amount.getValue();
		}
	};

	public int amountToApply(final Amount amount) {
		return amount.getValue();
	}
}
