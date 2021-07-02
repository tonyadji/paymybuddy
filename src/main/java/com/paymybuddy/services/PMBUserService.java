/**
 * 
 */
package com.paymybuddy.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.Account;
import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.exceptions.UserAlreadyExistsException;
import com.paymybuddy.repositories.AccountRepository;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.ui.form.RegisterForm;
import com.paymybuddy.utils.ExceptionMessageUtils;
import com.paymybuddy.utils.GenerateCodeUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class PMBUserService.
 *
 * @author tonys
 */
@Service
@Transactional
public class PMBUserService {

	/** The user repository. */
	private final PMBUserRepository userRepository;

	/** The account repository. */
	private final AccountRepository accountRepository;

	/** The password encoder. */
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * Instantiates a new PMB user service.
	 *
	 * @param userRepository the user repository
	 * @param accountRepository the account repository
	 * @param passwordEncoder the password encoder
	 */
	public PMBUserService(PMBUserRepository userRepository, AccountRepository accountRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the PMB user
	 */
	public PMBUser findByUsername(String username) {
		final Optional<PMBUser> user = userRepository.findByUsername(username);
		return user.orElse(null);
	}

	/**
	 * Creates the user.
	 *
	 * @param form the form
	 * @return the PMB user
	 */
	public PMBUser createUser(RegisterForm form) {
		final Optional<PMBUser> existingUser = userRepository.findByUsername(form.getUsername());
		if (existingUser.isPresent()) {
			throw new UserAlreadyExistsException(ExceptionMessageUtils.USER_ALREADY_EXISTS);
		}
		final PMBUser user = new PMBUser();
		user.setUsername(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setRole("USER");
		userRepository.save(user);
		final Account account = new Account(GenerateCodeUtils.generateXDigitNumber(10));
		account.setOwner(user);
		accountRepository.save(account);
		return user;
	}
}
