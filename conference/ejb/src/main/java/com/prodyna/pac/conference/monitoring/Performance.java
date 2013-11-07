package com.prodyna.pac.conference.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MBean implementation.
 * 
 * 
 */
public class Performance implements PerformanceMXBean {

	private Map<String, PerformanceEntry> entries = new HashMap<String, PerformanceEntry>();

	@Override
	public void report(String service, String method, long time)
	{
		String key = service + ":" + method;
		PerformanceEntry entry = entries.get(key);
		if (entry == null) {
			entry = new PerformanceEntry(service, method);
			entries.put(key, entry);
		}
		entry.report(time);
	}

	@Override
	public List<PerformanceEntry> getAll()
	{
		List<PerformanceEntry> entriesList = new ArrayList<PerformanceEntry>(
				entries.values());
		return entriesList;
	}

	@Override
	public int getCount()
	{
		return entries.size();
	}

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

	@Override
	public void reset()
	{
		entries.clear();
	}

}
