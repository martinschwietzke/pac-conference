package com.prodyna.pac.conference.common.model;

import java.beans.PropertyDescriptor;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * {@link ConstraintValidator} which checks if a given end date is behind a
 * given start date.
 * 
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class EndDateAfterStartDateConstraintValidator implements
		ConstraintValidator<CheckEndDataAfterStartDate, Object> {

	private String startDate;
	private String endDate;

	@Override
	public void initialize(CheckEndDataAfterStartDate constraintAnnotation)
	{
		startDate = constraintAnnotation.startDate();
		endDate = constraintAnnotation.endDate();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context)
	{
		boolean result = false;

		try {

			Date start = (Date) new PropertyDescriptor(startDate,
					value.getClass()).getReadMethod().invoke(value);
			Date end = (Date) new PropertyDescriptor(endDate, value.getClass())
					.getReadMethod().invoke(value);

			if (start != null && end != null && start.before(end)) {
				result = true;
			} else {
				context.buildConstraintViolationWithTemplate(
						"End date must be after start date!")
						.addConstraintViolation();
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Error while validating date range of class ["
							+ value.getClass().getName() + "]", e);
		}

		return result;
	}

}
