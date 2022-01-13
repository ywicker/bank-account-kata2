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

public class AccountServiceTest {
	private AccountService accountService;
	private Date now;

	@Before
	public void setup() {
		accountService = new AccountService();
		now = new Date();
	}

	/**
	 * Test nominaux du depot d'argent
	 */
	@Test
	public void depositTest() {
		// Test d'un seul depot
		assertThat(accountService.deposit(1, 2)).isEqualTo(2);
		assertThat(accountService.deposit(2, 10)).isEqualTo(10);

		// Test d'un second depot
		assertThat(accountService.deposit(1, 3)).isEqualTo(5);
	}

	/**
	 * Test des cas limite du depot d'argent
	 */
	@Test
	public void depositFailedTest() {
		// Test du depot d'un montant a 0
		assertThatThrownBy(() -> {
			accountService.deposit(1, 0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");

		// Test du depot d'un montant negatif
		assertThatThrownBy(() -> {
			accountService.deposit(1, -2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");
	}

	/**
	 * Test nominaux du retrait d'argent
	 */
	@Test
	public void withdrawalTest() {
		// Test d'un seul retrait avec solde negatif
		assertThat(accountService.withdrawal(1, 2)).isEqualTo(-2);

		// Test d'un second depot retrait avec solde negatif
		assertThat(accountService.withdrawal(1, 3)).isEqualTo(-5);

		// Test d'un retrait suite a un depot
		assertThat(accountService.deposit(2, 10)).isEqualTo(10);
		assertThat(accountService.withdrawal(2, 5)).isEqualTo(5);
	}

	/**
	 * Test des cas limite du retrait d'argent
	 */
	@Test
	public void withdrawalFailedTest() {
		// Test du retrait d'un montant a 0
		assertThatThrownBy(() -> {
			accountService.withdrawal(1, 0);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			accountService.withdrawal(1, -2);
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le retrait doit etre d'un montant superieur a 0");
	}

	/**
	 * Test nominaux du retrait d'argent
	 */
	@Test
	public void getAccountTest() {
		// Recuperation d'un compte vide
		final Account account1 = accountService.getAccount(1);
		assertThat(account1).extracting("balance").isEqualTo(0);
		assertThat(account1).extracting("operations").asList().isEmpty();

		// Test d'un second depot retrait avec solde negatif
		accountService.deposit(2, 2);
		final Account account2 = accountService.getAccount(2);
		assertThat(account2).extracting("balance").isEqualTo(2);
		assertThat(account2).extracting("operations").asList().hasSize(1)
		.extracting("amount", "type").containsExactly(
				tuple(2, OperationType.DEPOSIT));
		assertThat(account2).extracting("operations").extracting("date").asList()
			.matches(dates -> checkDatesRoughly(dates, now));

		// Test d'un retrait suite a un depot
		accountService.deposit(3, 10);
		accountService.withdrawal(3, 4);
		accountService.withdrawal(3, 6);
		final Account account3 = accountService.getAccount(3);
		assertThat(account3).extracting("balance").isEqualTo(0);
		assertThat(account3).extracting("operations").asList().hasSize(3)
			.extracting("amount", "type").containsExactly(
					tuple(10, OperationType.DEPOSIT),
					tuple(4, OperationType.WITHDRAWAL),
					tuple(6, OperationType.WITHDRAWAL));
		assertThat(account3).extracting("operations").extracting("date").asList()
			.matches(dates -> checkDatesRoughly(dates, now));
	}


	private boolean checkDatesRoughly(final List<?> dates, final Date now) {
		for(Object operationDate : dates) {
			if(!(operationDate instanceof Date)
					&& !compareDateRoughly((Date)operationDate, now)) {
				return false;
			}
		}
		return true;
	}
	private boolean compareDateRoughly(final Date operationDate, final Date now) {
		long timeStampNow = now.getTime();
		long timeStampOpDate = ((Date)operationDate).getTime();
		return timeStampNow > timeStampOpDate  && timeStampNow < (timeStampOpDate+86400);
	}
}
