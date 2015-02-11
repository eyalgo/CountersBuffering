package org.eyalgo.server.dropwizard.core;

import io.dropwizard.setup.Environment;

public interface ServicesFactory {

	CountersServices build(Environment environment);

}
