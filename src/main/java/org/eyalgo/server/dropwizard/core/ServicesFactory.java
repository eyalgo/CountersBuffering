package org.eyalgo.server.dropwizard.core;

import io.dropwizard.setup.Environment;

import org.eyalgo.server.dropwizard.CountersBufferConfiguration;

public interface ServicesFactory {

	CountersServices build(Environment environment, CountersBufferConfiguration configuration);

}
