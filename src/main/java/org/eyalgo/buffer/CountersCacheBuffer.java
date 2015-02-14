package org.eyalgo.buffer;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eyalgo.counters.Counterable;
import org.eyalgo.counters.CountersUpdater;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableMap;

public class CountersCacheBuffer implements CountersBufferIncrease, CacheBuffer {
	private final LoadingCache<Counterable, BufferValue> cache;
	private final CountersUpdater countersUpdater;
	private final int threashold;

	public CountersCacheBuffer(CountersUpdater countersUpdater, BufferConfiguration bufferConfiguration) {
		this.threashold = bufferConfiguration.getThreshold();
		this.countersUpdater = countersUpdater;
		//@formatter:off
		this.cache = CacheBuilder.newBuilder()
				.maximumSize(bufferConfiguration.getMaximumSize())
				.expireAfterWrite(bufferConfiguration.getExpireAfterWriteInSec(), TimeUnit.SECONDS)
				.expireAfterAccess(bufferConfiguration.getExpireAfterAccessInSec(), TimeUnit.SECONDS)
				.removalListener((notification) -> increaseCounter(notification))
				.build(new BufferValueCacheLoader());
		//@formatter:on
	}

	@Override
	public void increase(Counterable key) {
		BufferValue meter = cache.getUnchecked(key);
		int currentValue = meter.increment();
		if (currentValue > threashold) {
			if (meter.compareAndSet(currentValue, currentValue - threashold)) {
				increaseCounter(key, threashold);
			}
		}
	}

	@Override
	public Map<String, Integer> showBuffer() {
		//@formatter:off
		return ImmutableMap.copyOf(cache.asMap())
				.entrySet().stream()
				.collect(
						Collectors.toMap((entry) -> entry.getKey().toString(),
						(entry) -> entry.getValue().getValue()));
		//@formatter:on
	}

	private void increaseCounter(RemovalNotification<Object, Object> notification) {
		increaseCounter((Counterable) notification.getKey(), ((BufferValue) notification.getValue()).getValue());
	}

	private void increaseCounter(Counterable key, int amount) {
		countersUpdater.increaseCounter(key, amount);
	}
}
