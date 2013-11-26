package com.prodyna.pac.conference.common.interceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Inherited
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
/**
 * {@link Interceptor} binding annotation for {@link LoggingInterceptor} interceptor.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public @interface Logged {
}
