package org.eyalgo.buffer;

import com.google.common.base.MoreObjects;

public class BufferConfiguration {
	public final long maximumSize;
	public final long expireAfterWriteInSec;
	public final long expireAfterAccessInSec;
	public final int threashold;

	public BufferConfiguration(long maximumSize, long expireAfterWriteInSec, long expireAfterAccessInSec, int threashold) {
		this.maximumSize = maximumSize;
		this.expireAfterWriteInSec = expireAfterWriteInSec;
		this.expireAfterAccessInSec = expireAfterAccessInSec;
		this.threashold = threashold;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Maximum Size", maximumSize).add("Expire After Write In Sec", expireAfterWriteInSec).add("Expire After Access In Sec", expireAfterAccessInSec)
				.add("Threashold", threashold).toString();
	}
}
