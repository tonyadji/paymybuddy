/**
 * 
 */
package com.paymybuddy.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Transaction;
import com.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.utils.SecurityUtils;

/**
 * @author tonys
 *
 */
@Service
@Transactional
public class TransactionService {

	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}

	public List<Transaction> getTransactionHistory() {
		return transactionRepository.findByInitiatorUsernameOrderByCreatedDateDesc(SecurityUtils.getAuthUserName());
	}
}
