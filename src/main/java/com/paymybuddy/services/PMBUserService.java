/**
 * 
 */
package com.paymybuddy.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.exceptions.UserAlreadyExistsException;
import com.paymybuddy.form.RegisterForm;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;

/**
 * @author tonys
 *
 */
@Service
public class PMBUserService {
	
	private final PMBUserRepository userRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	public PMBUserService(PMBUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public PMBUser findByUsername(String username) {
		final Optional<PMBUser> user = userRepository.findByUsername(username);
		return user.orElse(null);
	}
	
	public PMBUser createUser(RegisterForm form) {
		final Optional<PMBUser> existingUser = userRepository.findByUsername(form.getUsername());
		if(existingUser.isPresent()) {
			throw new UserAlreadyExistsException(ExceptionMessageUtils.USER_ALREADY_EXISTS);
		}
		final PMBUser user = new PMBUser();
		user.setUsername(form.getUsername());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setRole("USER");
		return userRepository.save(user);
	}
}
