package com.prodyna.pac.conference.monitoring;

import java.util.List;

/**
 * MBean interface for collecting and serving performance indicator values.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public interface PerformanceMXBean {

	static final String OBJECT_NAME = "com.prodyna.pac.conference:service=Performance";

	void report(String service, String method, long time);

	List<PerformanceEntry> getAll();

	int getCount();

	PerformanceEntry getWorstMethodByTime();

	PerformanceEntry getWorstMethodByCount();

	void reset();

}
