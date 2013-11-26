package com.prodyna.pac.conference.common.interceptor;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import com.prodyna.pac.conference.common.monitoring.PerformanceMXBean;

/**
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Logged
@Interceptor
public class PerformanceInterceptor {

	@Inject
	private Logger log;

	private PerformanceMXBean performanceMBean;

	public PerformanceInterceptor() {

		MBeanServer ms = ManagementFactory.getPlatformMBeanServer();

		try {
			ObjectName objectName = new ObjectName(
					PerformanceMXBean.OBJECT_NAME);

			performanceMBean = MBeanServerInvocationHandler.newProxyInstance(
					ms, objectName, PerformanceMXBean.class, false);

		} catch (Exception e) {
			log.severe("Error while registering MBean ["
					+ PerformanceMXBean.OBJECT_NAME + "]. Error message: ["
					+ e.getMessage() + "]");
		}
	}

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception
	{

		long start = System.currentTimeMillis();

		Object proceed = ic.proceed();

		Method method = ic.getMethod();

		performanceMBean.report(method.getDeclaringClass().getName(),
				method.getName(), System.currentTimeMillis() - start);

		return proceed;
	}
}
