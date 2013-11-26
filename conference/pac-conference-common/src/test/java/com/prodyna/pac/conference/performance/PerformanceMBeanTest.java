package com.prodyna.pac.conference.performance;

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import com.prodyna.pac.conference.common.monitoring.Performance;
import com.prodyna.pac.conference.common.monitoring.PerformanceEntry;
import com.prodyna.pac.conference.common.monitoring.PerformanceMXBean;

public class PerformanceMBeanTest {

	private static PerformanceMXBean perf;

	@BeforeClass
	public static void beforeClass() throws Exception
	{
		MBeanServer ms = ManagementFactory.getPlatformMBeanServer();
		ObjectName objectName = new ObjectName(PerformanceMXBean.OBJECT_NAME);

		ms.registerMBean(new Performance(), objectName);

		perf = JMX.newMXBeanProxy(ms, objectName, PerformanceMXBean.class,
				false);
	}

	@After
	public void after() throws Exception
	{
		perf.reset();
	}

	@Test
	public void testPerformanceMXBean() throws Exception
	{

		int expectedCount = 4;
		int expectedMaxTime = 100;
		int expectedMinTime = 70;
		int expectedSum = 100 + 90 + 80 + 70;
		double expectedAvg = ((double) expectedSum) / ((double) 4);
		int maxMethodTime = 500;
		perf.report("MyServiceClass", "MyServiceMethod", expectedMaxTime);
		perf.report("MyServiceClass", "MyServiceMethod", 90);
		perf.report("MyServiceClass", "MyServiceMethod", 80);
		perf.report("MyServiceClass", "MyServiceMethod", expectedMinTime);
		perf.report("AnotherServiceClass", "MyServiceMethod", maxMethodTime);

		PerformanceEntry entry = perf.getWorstMethodByCount();

		Assert.assertEquals("MyServiceClass", entry.getService());
		Assert.assertEquals("MyServiceMethod", entry.getMethod());
		Assert.assertEquals(2, perf.getCount());
		Assert.assertEquals(expectedMaxTime, entry.getMaxTime());
		Assert.assertEquals(expectedCount, entry.getCount());
		Assert.assertEquals(expectedAvg, entry.getAverage(), 0);
		Assert.assertEquals(expectedMinTime, entry.getMinTime());
		Assert.assertEquals(expectedSum, entry.getSum());

		entry = perf.getWorstMethodByTime();

		Assert.assertEquals("AnotherServiceClass", entry.getService());
		Assert.assertEquals("MyServiceMethod", entry.getMethod());
		Assert.assertEquals(maxMethodTime, entry.getMaxTime());

	}
}
