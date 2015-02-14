package org.eyalgo.buffer;

import java.util.concurrent.atomic.AtomicInteger;

class BufferValue {
	private final AtomicInteger value;

	BufferValue() {
		value = new AtomicInteger(0);
	}

	public int getValue() {
		return value.get();
	}

	public int increment() {
		return value.incrementAndGet();
	}

	public boolean compareAndSet(int expect, int update) {
		return value.compareAndSet(expect, update);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
