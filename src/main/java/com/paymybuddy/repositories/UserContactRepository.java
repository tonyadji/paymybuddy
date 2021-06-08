/**
 * 
 */
package com.paymybuddy.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entities.UserContact;

/**
 * @author tonys
 *
 */
public interface UserContactRepository extends JpaRepository<UserContact, Long> {

	Optional<UserContact> findByOwnerAndAdded(String owner, String added);
	
	List<UserContact> findByOwner(String owner);
}
