package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
}
