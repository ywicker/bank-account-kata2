package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;

import com.lacombe.kata.model.Account;
import com.lacombe.kata.model.AccountStatement;
import com.lacombe.kata.model.Operation;
import com.lacombe.kata.model.Operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static com.lacombe.kata.model.OperationType.DEPOSIT;
import static com.lacombe.kata.model.OperationType.WITHDRAWAL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AccountTest {
	private Date now;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Before
	public void setup() {
		now = new Date();
	}

	@Test
	public void deposit() {
		Account account = new Account();
		account.deposit(now, 2);
		assertThat(account.getBalance()).isEqualTo(2);
		account.deposit(now, 3);
		assertThat(account.getBalance()).isEqualTo(5);
	}

	@Test
	public void depositFailed() {
		Account account = new Account();

		// Test du depot d'un montant a 0
		assertThatThrownBy(() -> {
			account.deposit(now, 0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");

		// Test du depot d'un montant negatif
		assertThatThrownBy(() -> {
			account.deposit(now, -2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");
	}

	@Test
	public void withdrawal() {
		Account account = new Account();
		account.withdrawal(now, 2);
		assertThat(account.getBalance()).isEqualTo(-2);
		account.withdrawal(now, 3);
		assertThat(account.getBalance()).isEqualTo(-5);
	}

	@Test
	public void withdrawalFailed() {
		Account account = new Account();

		// Test du retrait d'un montant a 0
		assertThatThrownBy(() -> {
			account.withdrawal(now, 0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			account.withdrawal(now, -2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");
	}

	@Test
	public void getOperations() {
		// Recuperation des operations d'un compte vide
		final Account account1 = new Account();
		assertThat(account1.getBalance()).isEqualTo(0);
		assertThat(account1.getOperations()).isEmpty();

		// Recuperation des operations d'un compte avec une seule operation
		final Account account2 = new Account();
		account2.deposit(now, 2);
		assertThat(account2.getBalance()).isEqualTo(2);
		assertThat(account2.getOperations()).hasSize(1).extracting("amount", "type")
			.containsExactly(tuple(2, DEPOSIT));
		assertThat(account2.getOperations()).extracting("date")
			.matches(dates -> checkDatesRoughly(dates, now));

		// Recuperation des operations d'un compte avec plusieurs operations
		final Account account3 = new Account();
		account3.deposit(now, 10);
		account3.withdrawal(now, 4);
		account3.withdrawal(now, 6);
		assertThat(account3.getBalance()).isEqualTo(0);
		assertThat(account3.getOperations()).hasSize(3).extracting("amount", "type")
			.containsExactly(
				tuple(10, DEPOSIT),
				tuple(4, WITHDRAWAL),
				tuple(6, WITHDRAWAL));
		assertThat(account3.getOperations()).extracting("date")
			.matches(dates -> checkDatesRoughly(dates, now));
	}

	@Test
	public void getAccountStatement() throws ParseException {
		// Test d'un compte vide
		final Account account1 = new Account();
		final AccountStatement account1Statement1 = account1.getAccountStatement(
				dateFormat.parse("2022-01-14 00:00:00"), dateFormat.parse("2022-01-15 00:00:00"));
		assertThat(account1Statement1.getOldBalance()).isEqualTo(0);
		assertThat(account1Statement1.getNewBalance()).isEqualTo(0);
		assertThat(account1Statement1.getOperations()).isEmpty();

		// Tests d'un compte avec plusieurs transactions
		final Account account2 = new Account();
		account2.deposit(dateFormat.parse("2021-01-20 09:00:00"), 10);
		account2.withdrawal(dateFormat.parse("2021-01-20 09:30:00"), 150);
		account2.withdrawal(dateFormat.parse("2021-10-14 05:00:00"), 150);
		account2.deposit(dateFormat.parse("2022-01-10 09:00:00"), 2000);
		account2.withdrawal(dateFormat.parse("2022-01-14 09:00:00"), 20);

		final AccountStatement account2Statement1 = account2.getAccountStatement(
				dateFormat.parse("2021-01-15 00:00:00"), dateFormat.parse("2022-01-15 00:00:00"));
		assertThat(account2Statement1.getOldBalance()).isEqualTo(0);
		assertThat(account2Statement1.getNewBalance())
			.isEqualTo(account2.getBalance()).isEqualTo(1690);
		assertThat(account2Statement1.getOperations()).hasSize(5);

		final AccountStatement account2Statement2 = account2.getAccountStatement(
				dateFormat.parse("2021-01-10 00:00:00"), dateFormat.parse("2021-01-12 00:00:00"));
		assertThat(account2Statement2.getOldBalance()).isEqualTo(0);
		assertThat(account2Statement2.getNewBalance()).isEqualTo(0);
		assertThat(account2Statement2.getOperations()).isEmpty();

		final AccountStatement account2Statement3 = account2.getAccountStatement(
				dateFormat.parse("2021-09-10 00:00:00"), dateFormat.parse("2022-01-14 00:00:00"));
		assertThat(account2Statement3.getOldBalance()).isEqualTo(-140);
		assertThat(account2Statement3.getNewBalance()).isEqualTo(1710);
		assertThat(account2Statement3.getOperations()).hasSize(2);
	}

	/**
	 * Permet de verifier qu'un ensemble de dates soient approximativement 
	 * egales a la date actuelle (Jour - 1)
	 * 
	 * @param operationDates : dates des operations testees
	 * @param now : Date de debut de l'execution des TUs
	 * @return true si les date sont approximativement egales a la date 
	 * actuelle (Jour - 1)
	 */
	private boolean checkDatesRoughly(final List<?> operationDates, final Date now) {
		for(Object operationDate : operationDates) {
			if((operationDate instanceof Date)
					&& !operationDate.equals(now)) {
				return false;
			}
		}
		return true;
	}
}
