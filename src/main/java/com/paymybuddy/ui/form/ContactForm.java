/**
 * 
 */
package com.paymybuddy.ui.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tonys
 *
 */
@NoArgsConstructor @Getter @Setter
public class ContactForm {

	@NotEmpty
	@Email
	private String username;
}
