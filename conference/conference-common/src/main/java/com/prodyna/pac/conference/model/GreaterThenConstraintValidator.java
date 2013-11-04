package com.prodyna.pac.conference.model;

import java.lang.annotation.Annotation;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GreaterThenConstraintValidator implements
		ConstraintValidator<GreaterThen, Date> {

	@Override
	public void initialize(GreaterThen constraintAnnotation)
	{
		String property = constraintAnnotation.property();
		

	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context)
	{
		//context.
		return false;
	}

}
