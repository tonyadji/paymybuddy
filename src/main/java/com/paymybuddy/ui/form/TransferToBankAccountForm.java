/**
 * 
 */
package com.paymybuddy.ui.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tonys
 *
 */
@NoArgsConstructor @Getter @Setter
public class TransferToBankAccountForm {

	@NotNull
	private BigDecimal amount;
	
	@NotEmpty
	private String bankAccount;
}
