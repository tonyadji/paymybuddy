/**
 * 
 */
package com.paymybuddy.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.paymybuddy.entities.enumerations.TransactionTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author tonys
 *
 */
@Table(name = "pmb_transaction")
@Entity
@Getter @Setter @ToString
public class Transaction extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "reference", unique = true)
	@NotEmpty	
	private String reference;

	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private BigDecimal comission;
	
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum transactionType;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
	private PMBUser initiator;
	
	@ManyToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = true)
	private PMBUser receiver;
	
	public Transaction() {
		
	}
	
}
