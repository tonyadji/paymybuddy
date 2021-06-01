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

/**
 * @author tonys
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PMBUserService userService;
	
	public UserDetailsServiceImpl(PMBUserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final PMBUser user = userService.findByUsername(username);
		if(user == null) throw new UsernameNotFoundException(username);
		final Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		return new User(user.getUsername(), user.getPassword(), authorities);
	}

}
