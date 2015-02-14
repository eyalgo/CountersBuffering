package org.eyalgo.server.dropwizard.core;

import org.eyalgo.buffer.CountersBuffer;
import org.eyalgo.counters.CountersRetriever;

public interface CountersServices {
	CountersRetriever getCountersRetriever();

	CountersBuffer getCountersBuffer();
}
