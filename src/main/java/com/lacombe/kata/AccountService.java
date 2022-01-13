package com.lacombe.kata;

import java.util.HashMap;

public class AccountService {

	private HashMap<Integer, Integer> accounts;

	public AccountService() {
		this.accounts = new HashMap<Integer, Integer>();
	}

	/**
	 * Permet de recuperer le solde d'un compte
	 * Retour un solde a 0 si le compte n'exite pas
	 * 
	 * @param idAccount
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
	 * @param idAccount
	 * @param amount
	 * @return
	 */
	public int deposit(final int idAccount, final int amount) {
		accounts.put(idAccount, amount + getBalance(idAccount));
		return getBalance(idAccount);
	}
}