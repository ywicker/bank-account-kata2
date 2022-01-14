package com.lacombe.kata;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.lacombe.kata.model.Account;
import com.lacombe.kata.model.Operations;

public class OperationsTest {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void getOperationsFailed() throws ParseException {
		Operations operations = new Operations();
		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			operations.getOperations(
					dateFormat.parse("2022-01-15 00:00:00"), dateFormat.parse("2022-01-14 00:00:00"));
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("La date de debut ne peut pas etre plus recente que la date de fin");

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			operations.getOperations(
					dateFormat.parse("1700-01-15 00:00:00"), dateFormat.parse("2022-01-15 00:00:00"));
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Les dates anterieures a 1900 ne sont pas gerees");

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			operations.getOperations(
					dateFormat.parse("2022-01-15 00:00:00"), null);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("La date ne peut pas etre null");
	}
}
