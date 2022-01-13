package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;

public class AccountServiceTest {
	private AccountService accountService;

	@Before
	public void setup() {
		accountService = new AccountService();
	}

	/**
	 * Test nominaux du depot d'argent
	 */
	@Test
	public void depositTest () {
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
	public void depositFailedTest () {
		// Test du depot d'un montant a 0
		assertThatThrownBy(new ThrowingCallable() {
			public void call() throws Throwable {
				  accountService.deposit(1, 0);
			}
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");

		// Test du depot d'un montant negatif
		assertThatThrownBy(new ThrowingCallable() {
			public void call() throws Throwable {
				  accountService.deposit(1, -2);
			}
		}).isInstanceOf(AssertionError.class)
		  .hasMessageContaining("Le depot doit etre d'un montant superieur a 0");
	}
}
