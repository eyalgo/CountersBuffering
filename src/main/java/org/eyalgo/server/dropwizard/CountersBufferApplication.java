package org.eyalgo.server.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.eyalgo.server.dropwizard.health.MongoConnectionHealth;
import org.eyalgo.server.dropwizard.resources.HelloWorldResource;

import com.mongodb.MongoClient;

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
		MongoClient client = configuration.getMongoConfiguration().build(environment);
		environment.healthChecks().register("mongo", new MongoConnectionHealth(client));
		environment.jersey().register(new HelloWorldResource("Hello, %s!", "You"));
	}
}
