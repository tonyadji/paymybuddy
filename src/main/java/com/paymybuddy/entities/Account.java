/**
 * 
 */
package com.paymybuddy.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Table(name = "pmb_account")
@Entity
@Getter @Setter @ToString
public class Account extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "account_number", unique = true)
	@NotEmpty	
	private String accountNumber;

	@NotNull
	private BigDecimal balance;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
	private PMBUser owner;
	
	public Account() {
		this.balance = BigDecimal.ZERO;
	}
	
	public Account(String accountNumber) {
		this.balance = BigDecimal.ZERO;
		this.accountNumber = accountNumber;
	}
}
