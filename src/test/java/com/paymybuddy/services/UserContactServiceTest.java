/**
 * 
 */
package com.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
@ExtendWith(MockitoExtension.class)
class UserContactServiceTest {

	@InjectMocks
	private UserContactService service;

	@Mock
	private UserContactRepository userContactRepository;

	@Mock
	private PMBUserRepository userRepository;
	
	private String owner;
	private String added;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		owner = "owner@own.com";
		added = "added@add.com";
	}

	/**
	 * Test method for
	 * {@link com.paymybuddy.services.UserContactService#createUserContact(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCreateUserContact() {
		// arrange

		when(userRepository.findByUsername(added)).thenReturn(Optional.of(new PMBUser()));
		when(userRepository.findByUsername(owner)).thenReturn(Optional.of(new PMBUser()));
		when(userContactRepository.findByOwnerAndAdded(owner, added)).thenReturn(Optional.empty());
		when(userContactRepository.save(ArgumentMatchers.any(UserContact.class))).thenReturn(new UserContact());

		// act
		final UserContact contact = service.createUserContact(owner, added);

		// assert
		assertNotNull(contact);
	}

	@Test
	void Given_OwnerNotFound_WhenCreatingContact_ThenThrowException() {
		// arrange
		when(userRepository.findByUsername(owner)).thenReturn(Optional.empty());

		// act
		final Exception ex = assertThrows(UserNotFoundException.class, () -> service.createUserContact(owner, added));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.USER_NOT_FOUND);
	}

	@Test
	void Given_AddedNotFound_WhenCreatingContact_ThenThrowException() {
		// arrange
		when(userRepository.findByUsername(owner)).thenReturn(Optional.of(new PMBUser()));
		when(userRepository.findByUsername(added)).thenReturn(Optional.empty());

		// act
		final Exception ex = assertThrows(UserNotFoundException.class, () -> service.createUserContact(owner, added));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.USER_NOT_FOUND);
	}

	@Test
	void Given_AlreadyExistingContact_WhenCreating_ThenThrowException() {
		// arrange
		when(userRepository.findByUsername(added)).thenReturn(Optional.of(new PMBUser()));
		when(userRepository.findByUsername(owner)).thenReturn(Optional.of(new PMBUser()));
		when(userContactRepository.findByOwnerAndAdded(owner, added)).thenReturn(Optional.of(new UserContact()));

		// act
		final Exception ex = assertThrows(ContactAlreadyExistsException.class, () -> service.createUserContact(owner, added));

		// assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.CONTACT_ALREADY_EXISTS);
	}

	/**
	 * Test method for
	 * {@link com.paymybuddy.services.UserContactService#isExistingContact(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testIsExistingContact_shouldReturnTrue() {
		//arrange
		when(userContactRepository.findByOwnerAndAdded(owner, added)).thenReturn(Optional.of(new UserContact()));
		//act
		final boolean result = service.isExistingContact(owner, added);
		
		assertTrue(result);
	}
	
	@Test
	void testIsExistingContact_shouldReturnFalse() {
		//arrange
		when(userContactRepository.findByOwnerAndAdded(owner, added)).thenReturn(Optional.empty());
		//act
		final boolean result = service.isExistingContact(owner, added);
		
		assertFalse(result);
	}

}
