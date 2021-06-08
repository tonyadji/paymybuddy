/**
 * 
 */
package com.paymybuddy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.entities.UserContact;
import com.paymybuddy.exceptions.ContactAlreadyExistsException;
import com.paymybuddy.exceptions.UserNotFoundException;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.repositories.UserContactRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;

/**
 * @author tonys
 *
 */
@Service
public class UserContactService {
	
	private final UserContactRepository userContactRepository;
	
	private final PMBUserRepository userRepository;

	public UserContactService(UserContactRepository userContactRepository, PMBUserRepository userRepository) {
		super();
		this.userContactRepository = userContactRepository;
		this.userRepository = userRepository;
	}

	public UserContact createUserContact(String owner, String added) {
		final Optional<PMBUser> ownerUser = userRepository.findByUsername(owner);
		if(!ownerUser.isPresent()) throw new UserNotFoundException(ExceptionMessageUtils.USER_NOT_FOUND);
		final Optional<PMBUser> addedUser = userRepository.findByUsername(added);
		if(!addedUser.isPresent()) throw new UserNotFoundException(ExceptionMessageUtils.USER_NOT_FOUND);
		if(isExistingContact(owner, added)) throw new ContactAlreadyExistsException(ExceptionMessageUtils.CONTACT_ALREADY_EXISTS);
		final UserContact contact = new UserContact();
		contact.setOwner(owner);
		contact.setAdded(added);
		return userContactRepository.save(contact);
	}
	
	public boolean isExistingContact(String owner, String added) {
		return userContactRepository.findByOwnerAndAdded(owner, added).isPresent();
	}

	public List<UserContact> findByOwner(String authUserName) {
		return userContactRepository.findByOwner(authUserName);
	}
}
