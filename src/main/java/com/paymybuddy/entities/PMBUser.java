/**
 * 
 */
package com.paymybuddy.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Table(name = "pmb_user")
@Entity
@Getter @Setter @ToString
public class PMBUser extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private String role;
}
