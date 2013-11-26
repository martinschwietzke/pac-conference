package com.prodyna.pac.conference.common.monitoring;

import java.util.List;

/**
 * MBean interface for collecting and serving performance indicator values.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface PerformanceMXBean {

	/**
	 * The unique object name for {@link PerformanceMXBean} MBean.
	 */
	static final String OBJECT_NAME = "com.prodyna.pac.conference:service=Performance";

	/**
	 * Reports the elapsed time for the given method invocaction.
	 * 
	 * @param service
	 *            Name of the service which has been called.
	 * @param method
	 *            Name of the service method which has been called.
	 * @param time
	 *            Elapsed time for method invocation.
	 */
	void report(String service, String method, long time);

	/**
	 * Lists all captured performance entries.
	 * 
	 * @return A list of all performance entries.
	 */
	List<PerformanceEntry> getAll();

	/**
	 * Return number of all reported {@link PerformanceEntry performance
	 * entries}
	 * 
	 * @return Number of reported {@link PerformanceEntry performance entries}
	 */
	int getCount();

	/**
	 * Returns the service method representing {@link PerformanceEntry} where
	 * the most longest ellapsed time has been reported.
	 * 
	 * @return Returns the service method representing {@link PerformanceEntry}
	 *         where the most longest ellapsed time has been reported.
	 */
	PerformanceEntry getWorstMethodByTime();

	/**
	 * Returns the service method representing {@link PerformanceEntry} which
	 * has been called most at all.
	 * 
	 * @return The service method representing {@link PerformanceEntry} which
	 *         has been called most at all.
	 */
	PerformanceEntry getWorstMethodByCount();

	/**
	 * Deletes all reported performance values.
	 */
	void reset();

}
