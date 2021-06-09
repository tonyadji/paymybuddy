/**
 * 
 */
package com.paymybuddy.ui.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.paymybuddy.validators.SamePasswordAnnotation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tonys
 *
 */
@NoArgsConstructor @Getter @Setter
@SamePasswordAnnotation
public class RegisterForm {

	@NotEmpty
	@Email
	private String username;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String repassword;
}
