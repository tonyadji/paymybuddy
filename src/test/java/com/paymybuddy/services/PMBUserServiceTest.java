package com.paymybuddy.services;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.paymybuddy.entities.PMBUser;
import com.paymybuddy.exceptions.UserAlreadyExistsException;
import com.paymybuddy.form.RegisterForm;
import com.paymybuddy.repositories.PMBUserRepository;
import com.paymybuddy.utils.ExceptionMessageUtils;

@ExtendWith(MockitoExtension.class)
class PMBUserServiceTest {

	@InjectMocks
	private PMBUserService service;

	@Mock
	private PMBUserRepository userRepository;
	
	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private RegisterForm form;

	@BeforeEach
	void setUp() throws Exception {
		form = new RegisterForm();
		form.setUsername("user1@email.com");
		form.setPassword("pass");
		form.setRepassword("pass");
	}

	@Test
	void testFindByUsername() {
		// arrange
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(new PMBUser()));

		// act
		final PMBUser result = service.findByUsername("the name");

		// assert
		assertThat(result).isNotNull();
	}

	@Test
	void testFindByUsername_ReturnNull() {
		// arrange
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

		// act
		final PMBUser result = service.findByUsername("the name");

		// assert
		assertThat(result).isNull();
	}

	@Test
	void given_ExistingUser_when_Creating_ThenThrowException() {
		// arrange
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(new PMBUser()));

		// act
		final UserAlreadyExistsException ex = assertThrows(UserAlreadyExistsException.class,
				() -> service.createUser(form));
		
		//assert
		assertThat(ex.getMessage()).isEqualTo(ExceptionMessageUtils.USER_ALREADY_EXISTS);
	}
	
	@Test
	void testCreateUser() {
		// arrange
		when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
		when(encoder.encode(ArgumentMatchers.anyString())).thenReturn("paoeri_kljamle");
		when(userRepository.save(ArgumentMatchers.any(PMBUser.class))).thenReturn(new PMBUser());
		// act
		final PMBUser result = service.createUser(form);
		
		//assert
		assertThat(result).isNotNull();
	}

}
