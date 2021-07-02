/**
 * 
 */
package com.paymybuddy.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.entities.PMBUser;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDetailsServiceImpl.
 *
 * @author tonys
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	/** The user service. */
	private final PMBUserService userService;
	
	/**
	 * Instantiates a new user details service impl.
	 *
	 * @param userService the user service
	 */
	public UserDetailsServiceImpl(PMBUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final PMBUser user = userService.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException(username);
		final Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
