package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lacombe.kata.model.Account;
import com.lacombe.kata.model.AccountStatement;
import com.lacombe.kata.model.DateProvider;
import com.lacombe.kata.model.DateProviderDefault;
import com.lacombe.kata.model.Operation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static com.lacombe.kata.model.OperationType.DEPOSIT;
import static com.lacombe.kata.model.OperationType.WITHDRAWAL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountTest {
	private DateProvider dateProvider;
	@Mock
	private DateProvider dateProviderMock;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date expectedDate;

	@Before
	public void setup() throws ParseException {
		MockitoAnnotations.initMocks(this);
		expectedDate = dateFormat.parse("2021-01-20 09:00:00");
		dateProvider = new DateProviderDefault();
	}

	@Test
	public void deposit() {
		Account account = new Account(dateProvider);
		account.deposit(2);
		assertThat(account.getBalance()).isEqualTo(2);
		account.deposit(3);
		assertThat(account.getBalance()).isEqualTo(5);
	}

	@Test
	public void depositFailed() {
		Account account = new Account(dateProvider);

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
		Account account = new Account(dateProvider);
		account.withdrawal(2);
		assertThat(account.getBalance()).isEqualTo(-2);
		account.withdrawal(3);
		assertThat(account.getBalance()).isEqualTo(-5);
	}

	@Test
	public void withdrawalFailed() {
		Account account = new Account(dateProvider);

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
		when(dateProviderMock.getDate()).thenReturn(expectedDate);

		// Recuperation des operations d'un compte vide
		final Account account1 = new Account(dateProvider);
		assertThat(account1.getBalance()).isEqualTo(0);
		assertThat(account1.getOperations()).isEmpty();

		// Recuperation des operations d'un compte avec une seule operation
		final Account account2 = new Account(dateProviderMock);
		account2.deposit(2);
		assertThat(account2.getBalance()).isEqualTo(2);
		assertThat(account2.getOperations()).hasSize(1).containsExactly(
				new Operation(expectedDate, 2, DEPOSIT));

		// Recuperation des operations d'un compte avec plusieurs operations
		final Account account3 = new Account(dateProviderMock);
		account3.deposit(10);
		account3.withdrawal(4);
		account3.withdrawal(6);
		assertThat(account3.getBalance()).isEqualTo(0);
		assertThat(account3.getOperations()).hasSize(3);
		assertThat(account3.getOperations()).containsExactly(
				new Operation(expectedDate, 10, DEPOSIT),
				new Operation(expectedDate, 4, WITHDRAWAL),
				new Operation(expectedDate, 6, WITHDRAWAL));
	}

	@Test
	public void getAccountStatement() throws ParseException {
		// Test d'un compte vide
		final Account account1 = new Account(dateProvider);
		final AccountStatement account1Statement1 = account1.getAccountStatement(
				dateFormat.parse("2022-01-14 00:00:00"), dateFormat.parse("2022-01-15 00:00:00"));
		assertThat(account1Statement1.getOldBalance()).isEqualTo(0);
		assertThat(account1Statement1.getNewBalance()).isEqualTo(0);
		assertThat(account1Statement1.getOperations()).isEmpty();

		// Tests d'un compte avec plusieurs transactions
		when(dateProviderMock.getDate()).thenReturn(dateFormat.parse("2021-01-20 09:00:00"),
				dateFormat.parse("2021-01-20 09:30:00"),
				dateFormat.parse("2021-10-14 05:00:00"),
				dateFormat.parse("2022-01-10 09:00:00"),
				dateFormat.parse("2022-01-14 09:00:00"));
		final Account account2 = new Account(dateProviderMock);
		account2.deposit(10);
		account2.withdrawal(150);
		account2.withdrawal(150);
		account2.deposit(2000);
		account2.withdrawal(20);

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
}
