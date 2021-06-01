/**
 * 
 */
package com.paymybuddy.entities.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

/**
 * @author tonys
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub
		return Optional.of("anonymous");
	}

}
