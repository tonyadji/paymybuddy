/**
 * 
 */
package com.paymybuddy.utils;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.paymybuddy.entities.PMBUser;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityUtils.
 *
 * @author tonys
 */
public class SecurityUtils {

	/**
	 * Instantiates a new security utils.
	 */
	private SecurityUtils() {}
	
	/**
	 * Gets the auth user.
	 *
	 * @return the auth user
	 */
	public static Authentication getAuthUser() {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		return securityContext.getAuthentication();
	}

	/**
	 * Gets the auth user name.
	 *
	 * @return the auth user name
	 */
	public static String getAuthUserName() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth instanceof AnonymousAuthenticationToken? "ANONYMOUS":((User) auth.getPrincipal()).getUsername();
	}
	
	/**
	 * Checks if is authenticated.
	 *
	 * @return true, if is authenticated
	 */
	public static boolean isAuthenticated() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			return ! (auth instanceof AnonymousAuthenticationToken);
		}catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Update security context.
	 *
	 * @param user the user
	 */
	public static void updateSecurityContext(PMBUser user) {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		final Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		final User securityUser = new User(user.getUsername(), user.getPassword(), authorities);
		securityContext.setAuthentication(
				new UsernamePasswordAuthenticationToken(securityUser, null, authorities)
		);
	}
}
