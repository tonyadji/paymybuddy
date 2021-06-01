/**
 * 
 */
package com.paymybuddy.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author tonys
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SamePasswordValidator.class)
public @interface SamePasswordAnnotation {

	String message() default "The password and the confirmation do not match";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
