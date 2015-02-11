package org.eyalgo.server.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.eyalgo.server.dropwizard.core.CountersServices;
import org.eyalgo.server.dropwizard.core.ServicesFactory;
import org.eyalgo.server.dropwizard.resources.CountersResource;
import org.eyalgo.server.dropwizard.resources.IncreaseCounterResource;

public class CountersBufferApplication extends Application<CountersBufferConfiguration> {

	public CountersBufferApplication() {
	}

	public static void main(String[] args) throws Exception {
		new CountersBufferApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<CountersBufferConfiguration> bootstrap) {
	}

	@Override
	public void run(CountersBufferConfiguration configuration, Environment environment) {
		ServicesFactory servicesFactory = configuration.getServicesFactory();
		CountersServices services = servicesFactory.build(environment);
		environment.jersey().register(new CountersResource(services.getCountersRetriever()));
		environment.jersey().register(new IncreaseCounterResource(services.getCountersUpdater()));
	}
}
