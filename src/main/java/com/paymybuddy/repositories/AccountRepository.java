/**
 * 
 */
package com.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.Account;

/**
 * @author tonys
 *
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByAccountNumber(String accountNumber);
	
	Optional<Account> findByOwnerUsername(String username);
}
