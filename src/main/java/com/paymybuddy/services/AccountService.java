/**
 * 
 */
package com.paymybuddy.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Account;
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;
import com.paymybuddy.utils.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tonys
 *
 */
@Service
@Transactional
@Slf4j
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void deposit(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) != 1) throw new IllegalArgumentException(ExceptionMessageUtils.INVALID_AMOUNT);
		final String username = SecurityUtils.getAuthUserName();
		Optional<Account> oAccount = accountRepository.findByOwnerUsername(username);
		if(oAccount.isPresent()) {
			final Account account = oAccount.get();
			account.setBalance(amount.add(account.getBalance()));
			log.debug("Self deposit {} - Amount of {}",username,amount);
			return;
		}
		throw new AccountNotFoundException(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}
}
