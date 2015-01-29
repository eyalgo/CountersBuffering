package org.eyalgo.counters;

import java.util.Map;

public interface CountersRetriever {
	Map<String, Integer> getAllCounters(Class<? extends Counterable> clazz);
}
