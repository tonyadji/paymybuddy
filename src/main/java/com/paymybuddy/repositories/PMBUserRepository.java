/**
 * 
 */
package com.paymybuddy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.PMBUser;

/**
 * @author tonys
 *
 */
public interface PMBUserRepository extends JpaRepository<PMBUser, Long> {

	Optional<PMBUser>  findByUsername(String username);
}
