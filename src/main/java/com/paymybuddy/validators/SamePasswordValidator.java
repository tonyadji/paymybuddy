package com.paymybuddy.validators;

import javax.validation.ConstraintValidatorContext;

import com.paymybuddy.ui.form.RegisterForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SamePasswordValidator implements javax.validation.ConstraintValidator<SamePasswordAnnotation, RegisterForm>{

	@Override
	public boolean isValid(RegisterForm value, ConstraintValidatorContext context) {
		try {
			return value.getPassword().equals(value.getRepassword());
		}catch(Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}

}
