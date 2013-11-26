package com.prodyna.pac.conference.common.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MBean implementation for {@link PerformanceMXBean} MBean.
 * 
 * @author Martin Schwietzke, PRODYNA AG
 * 
 */
public class Performance implements PerformanceMXBean {

	private Map<String, PerformanceEntry> entries = new HashMap<String, PerformanceEntry>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#report
	 * (java.lang.String, java.lang.String, long)
	 */
	@Override
	public void report(String service, String method, long time)
	{
		System.out.println("[" + service + "] [" + method + "] [" + time
				+ "]ms");

		String key = service + ":" + method;
		PerformanceEntry entry = entries.get(key);
		if (entry == null) {
			entry = new PerformanceEntry(service, method);
			entries.put(key, entry);
		}
		entry.report(time);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#getAll()
	 */
	@Override
	public List<PerformanceEntry> getAll()
	{
		List<PerformanceEntry> entriesList = new ArrayList<PerformanceEntry>(
				entries.values());
		return entriesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#getCount()
	 */
	@Override
	public int getCount()
	{
		return entries.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#
	 * getWorstMethodByTime()
	 */
	@Override
	public PerformanceEntry getWorstMethodByTime()
	{
		PerformanceEntry worstByTime = null;
		long worstTimeSum = -1;

		for (PerformanceEntry entry : entries.values()) {
			long sum = entry.getSum();
			if (sum > worstTimeSum) {
				worstTimeSum = sum;
				worstByTime = entry;
			}
		}

		return worstByTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#
	 * getWorstMethodByCount()
	 */
	@Override
	public PerformanceEntry getWorstMethodByCount()
	{
		PerformanceEntry worstByCount = null;
		long worstCount = -1;

		for (PerformanceEntry entry : entries.values()) {
			long count = entry.getCount();
			if (count > worstCount) {
				worstCount = count;
				worstByCount = entry;
			}
		}

		return worstByCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.prodyna.pac.conference.common.monitoring.PerformanceMXBean#reset()
	 */
	@Override
	public void reset()
	{
		entries.clear();
	}

}
