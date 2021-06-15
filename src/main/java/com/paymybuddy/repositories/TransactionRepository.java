/**
 * 
 */
package com.paymybuddy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.Transaction;

/**
 * @author tonys
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
