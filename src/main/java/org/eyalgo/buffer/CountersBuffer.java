package org.eyalgo.buffer;

import java.util.concurrent.TimeUnit;

import org.eyalgo.counters.Counterable;
import org.eyalgo.counters.CountersUpdater;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalNotification;

public class CountersBuffer {
	private final LoadingCache<Counterable, BufferValue> cache;
	private final CountersUpdater countersUpdater;
	private final int threashold;

	public CountersBuffer(CountersUpdater countersUpdater, BufferConfiguration cacheConfiguration) {
		this.threashold = cacheConfiguration.threashold;
		this.countersUpdater = countersUpdater;
		//@formatter:off
		this.cache = CacheBuilder.newBuilder()
				.maximumSize(cacheConfiguration.maximumSize)
				.expireAfterWrite(cacheConfiguration.expireAfterWriteInSec, TimeUnit.SECONDS)
				.expireAfterAccess(cacheConfiguration.expireAfterAccessInSec, TimeUnit.SECONDS)
				.removalListener((notification) -> increaseCounter(notification))
				.build(new BufferValueCacheLoader());
		//@formatter:on
	}

	public void inc(Counterable key) {
		BufferValue meter = cache.getUnchecked(key);
		int currentValue = meter.increment();
		if (currentValue > threashold) {
			if (meter.compareAndSet(currentValue, currentValue - threashold)) {
				increaseCounter(key, threashold);
			}
		}
	}

	private void increaseCounter(RemovalNotification<Object, Object> notification) {
		increaseCounter((Counterable) notification.getKey(), ((BufferValue) notification.getValue()).getValue());
	}

	private void increaseCounter(Counterable key, int amount) {
		countersUpdater.increaseCounter(key, amount);
	}
}
