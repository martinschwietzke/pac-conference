package com.prodyna.pac.conference.common.init;

import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.prodyna.pac.conference.common.monitoring.Performance;
import com.prodyna.pac.conference.common.monitoring.PerformanceMXBean;

/**
 * This EJB {@link Startup} class initializes all MBeans related to the
 * Conference application.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
@Startup
@Singleton
public class MBeanRegistrationStartup {

	@Inject
	private Logger log;

	@PostConstruct
	private void registerMBeans()
	{

		MBeanServer ms = ManagementFactory.getPlatformMBeanServer();

		try {
			log.info("Registering Performance MXBean ["
					+ PerformanceMXBean.OBJECT_NAME + "]");
			ms.registerMBean(new Performance(), new ObjectName(
					PerformanceMXBean.OBJECT_NAME));

		} catch (Exception e) {
			log.severe("Error while registering MBean ["
					+ PerformanceMXBean.OBJECT_NAME + "]. Error message: ["
					+ e.getMessage() + "]");
		}
	}

	@PreDestroy
	private void unregisterMBeans()
	{

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		try {
			log.info("Unregistering Performance MXBean ["
					+ PerformanceMXBean.OBJECT_NAME + "]");
			mbs.unregisterMBean(new ObjectName(PerformanceMXBean.OBJECT_NAME));

		} catch (Exception e) {
			log.severe("Error while unregistering MBean ["
					+ PerformanceMXBean.OBJECT_NAME + "]. Error message: ["
					+ e.getMessage() + "]");
		}
	}

}
