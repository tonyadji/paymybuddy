/**
 * 
 */
package com.paymybuddy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.paymybuddy.entities.audit.AuditorAwareImpl;



/**
 * @author tonys
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
public class JpaAuditingConfig {

	@Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}
