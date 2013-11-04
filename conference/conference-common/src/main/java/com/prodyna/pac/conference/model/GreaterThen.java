package com.prodyna.pac.conference.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Constraint(validatedBy = {})
@Documented
@Target(ElementType.FIELD)
public @interface GreaterThen {

	String property() default "";

}
