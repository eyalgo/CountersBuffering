package org.eyalgo.server.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.eyalgo.server.dropwizard.health.MongoConnectionHealth;
import org.eyalgo.server.dropwizard.jdbi.MongoFactory;
import org.eyalgo.server.dropwizard.jdbi.MongoFactory.MongoServices;
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
	public void run(CountersBufferConfiguration configuration, Environment environment) throws Exception {
		MongoFactory mongoFactory = configuration.getMongoFactory();
		MongoServices services = mongoFactory.build(environment);
		environment.healthChecks().register("mongo", new MongoConnectionHealth(services.client));
		environment.jersey().register(new CountersResource(services.countersRetriever));
		environment.jersey().register(new IncreaseCounterResource(services.countersUpdater));
	}
}
