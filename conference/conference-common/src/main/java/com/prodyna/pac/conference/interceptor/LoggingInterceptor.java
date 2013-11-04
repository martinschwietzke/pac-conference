package com.prodyna.pac.conference.interceptor;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Logged
@Interceptor
public class LoggingInterceptor {

	@Inject
	private Logger log;

	public LoggingInterceptor() {
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception
	{
		long start = System.currentTimeMillis();

		Object proceed = ic.proceed();

		if (log.isLoggable(Level.FINER)) {

			long time = System.currentTimeMillis() - start;

			Method method = ic.getMethod();

			String paramString = concatParameterValues(ic.getParameters());

			log.finer("CLASS: [" + method.getDeclaringClass().getName()
					+ "] METHOD: [" + method.getName() + "] PARAMETERS: "
					+ paramString + " TIME: [" + time + "ms]");
		}

		return proceed;
	}

	private String concatParameterValues(Object[] parameters)
	{

		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < parameters.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			Object param = parameters[i];
			sb.append(param.toString());
		}

		sb.append("]");

		return sb.toString();
	}
}
