package com.ssm.base.itface;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SelfGenderCaseValidator implements ConstraintValidator<SelfGenderCase, String> {

	String value;

	@Override
	public void initialize(SelfGenderCase constraintAnnotation) {
		value = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(String obj, ConstraintValidatorContext context) {
		if (value != null && obj != null && value != obj) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("gender should be " + value + "| the value is " + obj)
					.addConstraintViolation();
			return false;
		} else {
			return true;
		}
	}

}