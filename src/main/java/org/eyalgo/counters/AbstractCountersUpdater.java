package org.eyalgo.counters;

import static org.eyalgo.counters.CountersUtils.counterPrefix;

public abstract class AbstractCountersUpdater implements CountersUpdater {
	public AbstractCountersUpdater() {
	}

	@Override
	public void increaseCounter(Counterable counterable, int value) {
		String key = counterPrefix(counterable.getClass()) + counterable.counterKey();
		increaseCounter(key, value);
	}

	protected abstract void increaseCounter(String key, int value);
}
