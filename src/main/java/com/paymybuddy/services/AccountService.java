/**
 * 
 */
package com.paymybuddy.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.entities.enumerations.TransactionTypeEnum;
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.exceptions.InsufficientBalanceException;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;
import com.paymybuddy.utils.GenerateCodeUtils;
import com.paymybuddy.utils.SecurityUtils;
import com.paymybuddy.utils.TransactionFeeUtils;

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
	private final TransactionRepository transactionRepository;
	private final PMBUserRepository userRepository;

	public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository,
			PMBUserRepository userRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.userRepository = userRepository;
	}

	public void deposit(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException(ExceptionMessageUtils.INVALID_AMOUNT);
		final String username = SecurityUtils.getAuthUserName();
		Optional<Account> oAccount = accountRepository.findByOwnerUsername(username);
		if (oAccount.isPresent()) {
			final Account account = oAccount.get();
			final BigDecimal comission = calculateComission(amount);
			final BigDecimal amountToDeposit = amount.subtract(comission);
			account.setBalance(amountToDeposit.add(account.getBalance()));
			log.debug("Self deposit {} - Amount of {}", username, amount);
			final Transaction transaction = new Transaction();
			transaction.setTransactionType(TransactionTypeEnum.DEPOSIT);
			transaction.setAmount(amount);
			transaction.setAccountNumber(account.getAccountNumber());
			transaction.setInitiator(userRepository.findByUsername(username).orElse(null));
			transaction.setReference(GenerateCodeUtils.generateCode());
			transaction.setComission(comission);
			transactionRepository.save(transaction);
			return;
		}
		throw new AccountNotFoundException(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}

	public void transfer(String receiverUsername, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException(ExceptionMessageUtils.INVALID_AMOUNT);
		final String username = SecurityUtils.getAuthUserName();
		Optional<Account> oSenderAccount = accountRepository.findByOwnerUsername(username);
		if (oSenderAccount.isPresent()) {
			Optional<Account> oReceiverAccount = accountRepository.findByOwnerUsername(receiverUsername);
			if (oReceiverAccount.isPresent()) {
				final Account senderAccount = oSenderAccount.get();
				final Account receiverAccount = oReceiverAccount.get();
				final BigDecimal comission = calculateComission(amount);
				final BigDecimal amountDeducted = amount.add(comission);
				if (senderAccount.getBalance().compareTo(amountDeducted) >= 0) {
					senderAccount.setBalance(senderAccount.getBalance().subtract(amountDeducted));
					receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
					final Transaction transaction = new Transaction();
					transaction.setTransactionType(TransactionTypeEnum.TRANSFER);
					transaction.setAccountNumber(senderAccount.getAccountNumber());
					transaction.setAmount(amount);
					transaction.setInitiator(userRepository.findByUsername(username).orElse(null));
					transaction.setReference(GenerateCodeUtils.generateCode());
					transaction.setComission(comission);
					transaction.setReceiver(userRepository.findByUsername(receiverUsername).orElse(null));
					transactionRepository.save(transaction);
					return;
				}
				throw new InsufficientBalanceException(ExceptionMessageUtils.INSUFFICIENT_BALANCE);
			}
			throw new AccountNotFoundException(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
		}
		throw new AccountNotFoundException(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}
	
	public BigDecimal calculateComission(BigDecimal amount) {
		return amount.multiply(TransactionFeeUtils.TRANSACTION_FEES).divide(BigDecimal.valueOf(100));
	}
}
