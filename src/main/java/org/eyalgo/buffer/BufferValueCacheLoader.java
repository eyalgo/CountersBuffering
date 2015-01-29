package org.eyalgo.buffer;

import org.eyalgo.counters.Counterable;

import com.google.common.cache.CacheLoader;

public class BufferValueCacheLoader extends CacheLoader<Counterable, BufferValue> {
	@Override
	public BufferValue load(Counterable key) {
		return new BufferValue();
	}
}
