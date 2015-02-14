package org.eyalgo.buffer;

import java.util.Map;

import org.eyalgo.counters.Counterable;

public interface CountersBuffer {
	void increase(Counterable key);

	Map<String, Integer> showBuffer();
}
