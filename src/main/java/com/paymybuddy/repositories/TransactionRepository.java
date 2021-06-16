/**
 * 
 */
package com.paymybuddy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.Transaction;

/**
 * @author tonys
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByInitiatorUsernameOrderByCreatedDateDesc(String initiatorUsername);
}
