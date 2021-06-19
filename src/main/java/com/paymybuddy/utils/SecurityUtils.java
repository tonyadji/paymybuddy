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

import com.paymybuddy.entities.PMBUser;

/**
 * @author tonys
 *
 */
public class SecurityUtils {

	private SecurityUtils() {}
	
	public static Authentication getAuthUser() {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		return securityContext.getAuthentication();
	}

	public static String getAuthUserName() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth == null? "ANONYMOUS":auth.getName();
	}
	
	public static boolean isAuthenticated() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			return ! (auth instanceof AnonymousAuthenticationToken);
		}catch (Exception e) {
			return false;
		}
	}
	
	public static void updateSecurityContext(PMBUser user) {
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		final Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole()));
		securityContext.setAuthentication(
				new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities)
		);
	}
}
