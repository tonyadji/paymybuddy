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

/**
 * @author tonys
 *
 */
@Service
@Transactional
public class PMBUserService {

	private final PMBUserRepository userRepository;

	private final AccountRepository accountRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public PMBUserService(PMBUserRepository userRepository, AccountRepository accountRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public PMBUser findByUsername(String username) {
		final Optional<PMBUser> user = userRepository.findByUsername(username);
		return user.orElse(null);
	}

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
