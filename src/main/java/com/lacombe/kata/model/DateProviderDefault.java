package com.lacombe.kata.model;

import java.util.Date;

public class DateProviderDefault implements DateProvider {
	@Override
	public Date getDate() {
		return new Date();
	}
}
