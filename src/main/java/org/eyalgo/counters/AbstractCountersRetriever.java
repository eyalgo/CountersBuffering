package org.eyalgo.counters;

import static org.eyalgo.counters.CountersUtils.counterPrefix;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eyalgo.counters.impl.mongo.Counter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

public abstract class AbstractCountersRetriever implements CountersRetriever {
	public AbstractCountersRetriever() {
	}

	@Override
	public Map<String, Integer> getAllCounters(Class<? extends Counterable> clazz) {
		List<Counter> counters = counters();
		Builder<String, Integer> mapBuilder = ImmutableMap.builder();
		for (Counter counter : counters) {
			mapBuilder.put(extractId(counter, clazz), counter.getCount());
		}
		return mapBuilder.build();
	}

	abstract protected List<Counter> counters();

	private String extractId(Counter counter, Class<? extends Counterable> clazz) {
		return StringUtils.replace(counter.getId(), counterPrefix(clazz), StringUtils.EMPTY);
	}
}
