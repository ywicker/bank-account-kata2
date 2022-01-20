package com.lacombe.kata;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lacombe.kata.model.Account;
import com.lacombe.kata.model.DateProvider;
import com.lacombe.kata.model.DateProviderDefault;
import com.lacombe.kata.model.Operation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static com.lacombe.kata.model.OperationType.DEPOSIT;
import static com.lacombe.kata.model.OperationType.WITHDRAWAL;
import static com.lacombe.kata.model.Amount.createAmount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountTest {
	private DateProvider dateProvider;
	@Mock
	private DateProvider dateProviderMock;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		account.deposit(createAmount(2));
		assertThat(account.getBalance()).isEqualTo(2);
		account.deposit(createAmount(3));
		assertThat(account.getBalance()).isEqualTo(5);
	}

	@Test
	public void depositFailed() {
		Account account = new Account(dateProvider);

		// Test du depot d'un montant a 0
		assertThatThrownBy(() -> {
			account.deposit(createAmount(0));
		}).isInstanceOf(AssertionError.class);

		// Test du depot d'un montant negatif
		assertThatThrownBy(() -> {
			account.deposit(createAmount(-2));
		}).isInstanceOf(AssertionError.class);
	}

	@Test
	public void withdrawal() {
		Account account = new Account(dateProvider);
		account.withdrawal(createAmount(2));
		assertThat(account.getBalance()).isEqualTo(-2);
		account.withdrawal(createAmount(3));
		assertThat(account.getBalance()).isEqualTo(-5);
	}

	@Test
	public void withdrawalFailed() {
		Account account = new Account(dateProvider);

		// Test du retrait d'un montant a 0
		assertThatThrownBy(() -> {
			account.withdrawal(createAmount(0));
		}).isInstanceOf(AssertionError.class);

		// Test du retrait d'un montant negatif
		assertThatThrownBy(() -> {
			account.withdrawal(createAmount(-2));
		}).isInstanceOf(AssertionError.class);
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
		account2.deposit(createAmount(2));
		assertThat(account2.getBalance()).isEqualTo(2);
		assertThat(account2.getOperations()).hasSize(1).containsExactly(
				new Operation(expectedDate, createAmount(2), DEPOSIT));

		// Recuperation des operations d'un compte avec plusieurs operations
		final Account account3 = new Account(dateProviderMock);
		account3.deposit(createAmount(10));
		account3.withdrawal(createAmount(4));
		account3.withdrawal(createAmount(6));
		assertThat(account3.getBalance()).isEqualTo(0);
		assertThat(account3.getOperations()).hasSize(3);
		assertThat(account3.getOperations()).containsExactly(
				new Operation(expectedDate, createAmount(10), DEPOSIT),
				new Operation(expectedDate, createAmount(4), WITHDRAWAL),
				new Operation(expectedDate, createAmount(6), WITHDRAWAL));
	}

	@Test
	public void getAccountStatement() throws ParseException {
		// Test d'un compte vide
		final Account account1 = new Account(dateProvider);
		assertThat(account1.getAccountStatement(
				dateFormat.parse("2022-01-14 00:00:00"), 
				dateFormat.parse("2022-01-15 00:00:00")))
			.isEmpty();

		// Tests d'un compte avec plusieurs transactions
		when(dateProviderMock.getDate()).thenReturn(dateFormat.parse("2021-01-20 09:00:00"),
				dateFormat.parse("2021-01-20 09:30:00"),
				dateFormat.parse("2021-10-14 05:00:00"),
				dateFormat.parse("2022-01-10 09:00:00"),
				dateFormat.parse("2022-01-14 09:00:00"));
		final Account account2 = new Account(dateProviderMock);
		account2.deposit(createAmount(10));
		account2.withdrawal(createAmount(150));
		account2.withdrawal(createAmount(150));
		account2.deposit(createAmount(2000));
		account2.withdrawal(createAmount(20));

		assertThat(account2.getAccountStatement(
				dateFormat.parse("2021-01-15 00:00:00"), 
				dateFormat.parse("2022-01-15 00:00:00")))
			.hasSize(5);

		assertThat(account2.getAccountStatement(
				dateFormat.parse("2021-01-10 00:00:00"), 
				dateFormat.parse("2021-01-12 00:00:00")))
			.isEmpty();

		assertThat(account2.getAccountStatement(
				dateFormat.parse("2021-09-10 00:00:00"), 
				dateFormat.parse("2022-01-14 00:00:00")))
			.hasSize(2);
	}
}
