/**
 * 
 */
package com.paymybuddy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.entities.UserContact;
import com.paymybuddy.exceptions.ContactAlreadyExistsException;
import com.paymybuddy.exceptions.SelfContactException;
import com.paymybuddy.exceptions.UserNotFoundException;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.repositories.UserContactRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class UserContactService.
 *
 * @author tonys
 */
@Service
@Transactional
public class UserContactService {
	
	/** The user contact repository. */
	private final UserContactRepository userContactRepository;
	
	/** The user repository. */
	private final PMBUserRepository userRepository;

	/**
	 * Instantiates a new user contact service.
	 *
	 * @param userContactRepository the user contact repository
	 * @param userRepository the user repository
	 */
	public UserContactService(UserContactRepository userContactRepository, PMBUserRepository userRepository) {
		super();
		this.userContactRepository = userContactRepository;
		this.userRepository = userRepository;
	}

	/**
	 * Creates the user contact.
	 *
	 * @param owner the owner
	 * @param added the added
	 * @return the user contact
	 */
	public UserContact createUserContact(String owner, String added) {
		if(owner.equals(added)) throw new SelfContactException(ExceptionMessageUtils.CANT_BE_SELF_CONTACT);
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
	
	/**
	 * Checks if is existing contact.
	 *
	 * @param owner the owner
	 * @param added the added
	 * @return true, if is existing contact
	 */
	public boolean isExistingContact(String owner, String added) {
		return userContactRepository.findByOwnerAndAdded(owner, added).isPresent();
	}

	/**
	 * Find by owner.
	 *
	 * @param authUserName the auth user name
	 * @return the list
	 */
	public List<UserContact> findByOwner(String authUserName) {
		return userContactRepository.findByOwner(authUserName);
	}
}
