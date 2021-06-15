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
import com.paymybuddy.exceptions.AccountNotFoundException;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;

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

		// act
		service.deposit(BigDecimal.valueOf(3000));

		// assert
		assertThat(account.getBalance()).isEqualTo(BigDecimal.valueOf(3000));
	}

	@Test
	void testDeposit_shouldThrowException() {
		// arrange
		when(accountRepository.findByOwnerUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

		// act
		final Exception ex = assertThrows(AccountNotFoundException.class,
				() -> service.deposit(BigDecimal.valueOf(3000)));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.ACCOUNT_NOT_FOUND);
	}

}
