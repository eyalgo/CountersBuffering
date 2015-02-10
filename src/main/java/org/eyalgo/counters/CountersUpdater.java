package org.eyalgo.counters;


public interface CountersUpdater {

	public abstract void increaseCounter(Counterable counterable, int value);

}
