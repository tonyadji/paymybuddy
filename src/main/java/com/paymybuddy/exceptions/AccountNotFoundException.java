/**
 * 
 */
package com.paymybuddy.exceptions;

/**
 * @author tonys
 *
 */
public class AccountNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
