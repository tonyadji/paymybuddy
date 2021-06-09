/**
 * 
 */
package com.paymybuddy.ui.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author tonys
 *
 */
@NoArgsConstructor @Getter @Setter
public class AccountDepositForm {

	@NotNull
	private BigDecimal amount;
}
