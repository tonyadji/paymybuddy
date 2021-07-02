/**
 * 
 */
package com.paymybuddy.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.UserContact;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.repositories.UserContactRepository;
import com.paymybuddy.ui.ProfilUI;
import com.paymybuddy.utils.SecurityUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class ProfileService.
 *
 * @author tonys
 */
@Service
@Transactional
public class ProfileService {

	/** The user contact repository. */
	private UserContactRepository userContactRepository;

	/** The account repository. */
	private AccountRepository accountRepository;

	/**
	 * Instantiates a new profile service.
	 *
	 * @param userContactRepository the user contact repository
	 * @param accountRepository the account repository
	 */
	public ProfileService(UserContactRepository userContactRepository, AccountRepository accountRepository) {
		this.userContactRepository = userContactRepository;
		this.accountRepository = accountRepository;
	}

	/**
	 * Gets the profile informations.
	 *
	 * @return the profile informations
	 */
	public ProfilUI getProfileInformations() {
		final ProfilUI informations = new ProfilUI();
		final String username = SecurityUtils.getAuthUserName();
		informations.setUsername(username);
		informations.setContacts(userContactRepository.findByOwner(username).stream().map(UserContact::getAdded)
				.collect(Collectors.toList()));
		final Optional<Account> account = accountRepository.findByOwnerUsername(username);
		if (account.isPresent()) {
			informations.setBalance(account.get().getBalance());
			informations.setAccountNumber(account.get().getAccountNumber());
		}
		return informations;
	}
}
