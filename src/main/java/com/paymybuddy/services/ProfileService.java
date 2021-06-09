/**
 * 
 */
package com.paymybuddy.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Account;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.repositories.UserContactRepository;
import com.paymybuddy.ui.ProfilUI;
import com.paymybuddy.utils.SecurityUtils;

/**
 * @author tonys
 *
 */
@Service
@Transactional
public class ProfileService {
	
	private UserContactRepository userContactRepository;
	
	private AccountRepository accountRepository;
	
	public ProfileService(UserContactRepository userContactRepository, AccountRepository accountRepository) {
		this.userContactRepository = userContactRepository;
		this.accountRepository = accountRepository;
	}

	public ProfilUI getProfileInformations() {
		final ProfilUI informations = new ProfilUI();
		final String username = SecurityUtils.getAuthUserName();
		informations.setUsername(username);
		informations.setContacts(userContactRepository.findByOwner(username).size());
		final Optional<Account> account = accountRepository.findByOwnerUsername(username);
		if(account.isPresent()) {
			informations.setBalance(account.get().getBalance());
		}
		return informations;
	}
}
