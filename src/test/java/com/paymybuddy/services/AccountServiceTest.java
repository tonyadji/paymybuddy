/**
 * 
 */
package com.paymybuddy.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.entities.Transaction;
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.exceptions.InsufficientBalanceException;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;
import com.paymybuddy.utils.TransactionFeeUtils;

/**
 * @author tonys
 *
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	@InjectMocks
	private AccountService service;

	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private PMBUserRepository userRepository;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * Test method for
	 * {@link com.paymybuddy.services.AccountService#deposit(java.math.BigDecimal)}.
	 */
	@Test
	void testDeposit() {
		// arrange
		final Account account = new Account();
		when(accountRepository.findByOwnerUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(account));
		when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(new Transaction());
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(new PMBUser()));
		// act
		service.deposit(BigDecimal.valueOf(3000));
		final BigDecimal comission = BigDecimal.valueOf(3000).multiply(TransactionFeeUtils.TRANSACTION_FEES).divide(BigDecimal.valueOf(100));
		// assert
		assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(3000).subtract(comission));
	}

	@Test
	void testDeposit_shouldThrowAccountException() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(3000);
		when(accountRepository.findByOwnerUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

		// act
		final Exception ex = assertThrows(AccountNotFoundException.class,
				() -> service.deposit(amount));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}
	
	@Test
	void testDeposit_shouldThrowIllegalArgumentException() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(-3000);
		// act
		final Exception ex = assertThrows(IllegalArgumentException.class,
				() -> service.deposit(amount));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.INVALID_AMOUNT);
	}
	
	@Test
	void testTransfer_shouldThrowIllegalArgumentException() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(-3000);
		// act
		final Exception ex = assertThrows(IllegalArgumentException.class,
				() -> service.transfer("receiver",amount));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.INVALID_AMOUNT);
	}

	@Test
	void testTransfer_shouldThrow_AccountNotFoundException() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(3000);
		when(accountRepository.findByOwnerUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		// act
		final Exception ex = assertThrows(AccountNotFoundException.class,
				() -> service.transfer("reciver", amount));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}
	
	@Test
	void testTransfer_shouldThrow_InsufficientBalanceException() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(3000);
		final Account senderAccount = new Account();
		final Account receiverAccount = new Account();
		when(accountRepository.findByOwnerUsername("ANONYMOUS")).thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByOwnerUsername("receiver")).thenReturn(Optional.of(receiverAccount));
		// act
		final Exception ex = assertThrows(InsufficientBalanceException.class,
				() -> service.transfer("receiver",amount));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.INSUFFICIENT_BALANCE);
	}
	
	@Test
	void testTransfer_shouldTransferMoney() {
		// arrange
		final BigDecimal amount = BigDecimal.valueOf(3000);
		final Account senderAccount = new Account();
		senderAccount.setBalance(BigDecimal.valueOf(5000));
		final Account receiverAccount = new Account();
		when(accountRepository.findByOwnerUsername("ANONYMOUS")).thenReturn(Optional.of(senderAccount));
		when(accountRepository.findByOwnerUsername("receiver")).thenReturn(Optional.of(receiverAccount));
		when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(new Transaction());
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(new PMBUser()));
		// act
		service.transfer("receiver",amount);

		// assert
		assertThat(receiverAccount.getBalance().compareTo(BigDecimal.valueOf(3000))).isEqualTo(0);
	}
}
