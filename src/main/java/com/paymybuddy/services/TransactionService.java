/**
 * 
 */
package com.paymybuddy.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Transaction;
import com.paymybuddy.repositories.TransactionRepository;
import com.paymybuddy.utils.SecurityUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionService.
 *
 * @author tonys
 */
@Service
@Transactional
public class TransactionService {

	/** The transaction repository. */
	private final TransactionRepository transactionRepository;

	/**
	 * Instantiates a new transaction service.
	 *
	 * @param transactionRepository the transaction repository
	 */
	public TransactionService(TransactionRepository transactionRepository) {
		super();
		this.transactionRepository = transactionRepository;
	}

	/**
	 * Gets the transaction history.
	 *
	 * @return the transaction history
	 */
	public List<Transaction> getTransactionHistory() {
		return transactionRepository.findByInitiatorUsernameOrderByCreatedDateDesc(SecurityUtils.getAuthUserName());
	}

	/**
	 * Gets the paginated transaction history.
	 *
	 * @param pageRequest the page request
	 * @return the paginated transaction history
	 */
	public Page<Transaction> getPaginatedTransactionHistory(Pageable pageRequest) {
		return transactionRepository.findByInitiatorUsernameOrderByCreatedDateDesc(SecurityUtils.getAuthUserName(),
				pageRequest);
	}
}
