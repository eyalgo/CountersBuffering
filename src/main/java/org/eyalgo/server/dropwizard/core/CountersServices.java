package org.eyalgo.server.dropwizard.core;

import org.eyalgo.buffer.CountersBufferIncrease;
import org.eyalgo.counters.CountersRetriever;

public interface CountersServices {
	CountersRetriever getCountersRetriever();
	CountersBufferIncrease getCountersBufferIncrease();
}
