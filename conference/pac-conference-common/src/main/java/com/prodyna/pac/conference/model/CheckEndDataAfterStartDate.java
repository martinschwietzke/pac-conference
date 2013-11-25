package com.prodyna.pac.conference.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = { EndDateAfterStartDateConstraintValidator.class })
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEndDataAfterStartDate {

	String message() default "{invalid.daterange}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String startDate() default "";

	String endDate() default "";

}
