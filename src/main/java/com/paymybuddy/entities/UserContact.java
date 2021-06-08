/**
 * 
 */
package com.paymybuddy.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Table(name = "pmb_contact")
@Entity
@Getter @Setter @ToString
public class UserContact extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Email @NotEmpty
	private String owner;
	
	@Email @NotEmpty
	private String added;
}
