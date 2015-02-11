package org.eyalgo.server.dropwizard;

import io.dropwizard.Configuration;

import org.eyalgo.server.dropwizard.jdbi.MongoServicesFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountersBufferConfiguration extends Configuration {
	@JsonProperty("mongo")
	private MongoServicesFactory servicesFactory = new MongoServicesFactory();

	public CountersBufferConfiguration() {
	}

	public MongoServicesFactory getServicesFactory() {
		return servicesFactory;
	}

	public void setMongoFactory(MongoServicesFactory mongoFactory) {
		this.servicesFactory = mongoFactory;
	}
}
