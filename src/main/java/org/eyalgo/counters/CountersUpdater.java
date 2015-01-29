package org.eyalgo.counters;

public interface CountersUpdater {
	void increaseCounter(Counterable key, int value);
}
