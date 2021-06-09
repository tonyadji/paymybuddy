/**
 * 
 */
package com.paymybuddy.integration.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paymybuddy.entities.Account;
import com.paymybuddy.repositories.AccountRepository;

/**
 * @author tonys
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class AccountRepositoryIT {

	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	void Given_CorrectUsername_When_FindByOwnerUsername_ThenReturnTheAccount() {
		final String username = "ceo@mastermin.co";
		final Optional<Account> account = accountRepository.findByOwnerUsername(username);
		//assert
		assertThat(account.isPresent()).isEqualTo(true);
	}
}
