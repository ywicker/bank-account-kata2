package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;

import com.lacombe.kata.model.Account;
import com.lacombe.kata.model.OperationType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Date;
import java.util.List;

public class AccountTest {
	private Date now;

	@Before
	public void setup() {
		now = new Date();
	}

	@Test
	public void deposit() {
		Account account = new Account();
		account.deposit(2);
		assertThat(account.getBalance()).isEqualTo(2);
		account.deposit(3);
		assertThat(account.getBalance()).isEqualTo(5);
	}

	@Test
	public void depositFailed() {
		Account account = new Account();

		// Test du depot d'un montant a 0
		assertThatThrownBy(() -> {
			account.deposit(0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");

		// Test du depot d'un montant negatif
		assertThatThrownBy(() -> {
			account.deposit(-2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");
	}

	@Test
	public void withdrawal() {
		Account account = new Account();
		account.withdrawal(2);
		assertThat(account.getBalance()).isEqualTo(-2);
		account.withdrawal(3);
		assertThat(account.getBalance()).isEqualTo(-5);
	}

	@Test
	public void withdrawalFailed() {
		Account account = new Account();

		// Test du retrait d'un montant a 0
		assertThatThrownBy(() -> {
			account.withdrawal(0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			account.withdrawal(-2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");
	}

	@Test
	public void getOperations() {
		// Recuperation d'un compte vide
		final Account account1 = new Account();
		assertThat(account1.getBalance()).isEqualTo(0);
		assertThat(account1.getOperations()).isEmpty();

		// Recuperation d'un compte avec une seule operation
		final Account account2 = new Account();
		account2.deposit(2);
		assertThat(account2.getBalance()).isEqualTo(2);
		assertThat(account2.getOperations()).hasSize(1).extracting("amount", "type")
			.containsExactly(tuple(2, OperationType.DEPOSIT));
		assertThat(account2.getOperations()).extracting("date")
			.matches(dates -> checkDatesRoughly(dates, now));

		// Recuperation d'un compte avec plusieurs operations
		final Account account3 = new Account();
		account3.deposit(10);
		account3.withdrawal(4);
		account3.withdrawal(6);
		assertThat(account3.getBalance()).isEqualTo(0);
		assertThat(account3.getOperations()).hasSize(3).extracting("amount", "type")
			.containsExactly(
				tuple(10, OperationType.DEPOSIT),
				tuple(4, OperationType.WITHDRAWAL),
				tuple(6, OperationType.WITHDRAWAL));
		assertThat(account3.getOperations()).extracting("date")
			.matches(dates -> checkDatesRoughly(dates, now));
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
			if(!(operationDate instanceof Date)
					&& !compareDateRoughly((Date)operationDate, now)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de verifier qu'une de date soit approximativement 
	 * egale a la date actuelle (Jour - 1)
	 * 
	 * @param operationDate : date de l'operations testee
	 * @param now : Date de debut de l'execution des TUs
	 * @return true si la date est approximativement egale a la date 
	 * actuelle (Jour - 1)
	 */
	private boolean compareDateRoughly(final Date operationDate, final Date now) {
		long timeStampNow = now.getTime();
		long timeStampOpDate = ((Date)operationDate).getTime();
		return timeStampNow > timeStampOpDate  && timeStampNow < (timeStampOpDate+86400);
	}
}
