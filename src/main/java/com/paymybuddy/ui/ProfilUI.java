/**
 * 
 */
package com.paymybuddy.ui;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Getter @Setter @ToString
public class ProfilUI {

	private String username;
	
	private String accountNumber;
	
	private List<String> contacts;
	
	private BigDecimal balance;
	
	public ProfilUI() {
		this.balance = BigDecimal.ZERO;
	}
}
