package com.tech.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Value("${atm.withdraw.limit}")
	private String withdrawLimit;

	public String getWithdrawLimit() {
		return withdrawLimit;
	}

}
