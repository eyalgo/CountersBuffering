package org.eyalgo.server.dropwizard.core;

import org.eyalgo.counters.CountersRetriever;
import org.eyalgo.counters.CountersUpdater;

public interface CountersServices {
	CountersRetriever getCountersRetriever();
	CountersUpdater getCountersUpdater();
}
