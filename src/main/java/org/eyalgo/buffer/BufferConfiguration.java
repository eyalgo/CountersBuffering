package org.eyalgo.buffer;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BufferConfiguration {
	@NotEmpty
	@JsonProperty
	private long maximumSize = 500;
	@JsonProperty
	private long expireAfterWriteInSec = 60;
	@JsonProperty
	private long expireAfterAccessInSec = 60;
	@JsonProperty
	private int threshold = 500;

	public BufferConfiguration() {
	}

	public BufferConfiguration(long maximumSize, long expireAfterWriteInSec, long expireAfterAccessInSec, int threshold) {
		this.maximumSize = maximumSize;
		this.expireAfterWriteInSec = expireAfterWriteInSec;
		this.expireAfterAccessInSec = expireAfterAccessInSec;
		this.threshold = threshold;
	}

	public long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public long getExpireAfterWriteInSec() {
		return expireAfterWriteInSec;
	}

	public void setExpireAfterWriteInSec(long expireAfterWriteInSec) {
		this.expireAfterWriteInSec = expireAfterWriteInSec;
	}

	public long getExpireAfterAccessInSec() {
		return expireAfterAccessInSec;
	}

	public void setExpireAfterAccessInSec(long expireAfterAccessInSec) {
		this.expireAfterAccessInSec = expireAfterAccessInSec;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("Maximum Size", maximumSize).add("Expire After Write In Sec", expireAfterWriteInSec).add("Expire After Access In Sec", expireAfterAccessInSec)
				.add("Threshold", threshold).toString();
	}
}
