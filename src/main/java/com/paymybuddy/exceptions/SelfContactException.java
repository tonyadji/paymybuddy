/**
 * 
 */
package com.paymybuddy.exceptions;

/**
 * @author tonys
 *
 */
public class SelfContactException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelfContactException(String message) {
		super(message);
	}
}
