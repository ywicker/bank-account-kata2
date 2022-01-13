package com.lacombe.kata.model;

public enum OperationType {
	DEPOSIT("Depot"),
	WITHDRAWAL("Retrait");

	public String getLabel() {
		return label;
	}

	private final String label;

	private OperationType(String label) {
		this.label = label;
	}
}
