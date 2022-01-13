package com.lacombe.kata;

import java.util.HashMap;

public class AccountService {

	private String DEPOSIT_ERROR_MESSAGE = "Le depot doit etre d'un montant superieur a 0";
	private String WITHDRAWAL_ERROR_MESSAGE = "Le retrait doit etre d'un montant superieur a 0";

	private HashMap<Integer, Integer> accounts;

	public AccountService() {
		this.accounts = new HashMap<Integer, Integer>();
	}

	/**
	 * Permet de recuperer le solde d'un compte
	 * Retour un solde a 0 si le compte n'exite pas
	 * 
	 * @param idAccount l'identifiant du compte
	 * @return le solde du compte
	 */
	private int getBalance(final int idAccount) {
		if(accounts.containsKey(idAccount)) {
			return accounts.get(idAccount);
		} else {
			return 0;
		}
	}

	/**
	 * Deposer de l'argent sur le compte
	 * 
	 * @param idAccount l'identifiant du compte
	 * @param amount le montant a deposer
	 * @return le solde du compte
	 */
	public int deposit(final int idAccount, final int amount) {
		assert amount > 0 : DEPOSIT_ERROR_MESSAGE;

		accounts.put(idAccount, amount + getBalance(idAccount));
		return getBalance(idAccount);
	}

	/**
	 * Retirer de l'argent sur le compte
	 * 
	 * @param idAccount l'identifiant du compte
	 * @param amount le montant a prelever
	 * @return le solde du compte
	 */
	public int withdrawal(final int idAccount, final int amount) {
		assert amount > 0 : WITHDRAWAL_ERROR_MESSAGE;

		accounts.put(idAccount, getBalance(idAccount) - amount);
		return getBalance(idAccount);
	}
}