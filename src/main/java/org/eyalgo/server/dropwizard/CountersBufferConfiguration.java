package org.eyalgo.server.dropwizard;

import io.dropwizard.Configuration;

import org.eyalgo.buffer.BufferConfiguration;
import org.eyalgo.server.dropwizard.configuration_factories.MongoServicesFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountersBufferConfiguration extends Configuration {
	@JsonProperty("mongo")
	private MongoServicesFactory servicesFactory = new MongoServicesFactory();
	@JsonProperty("buffer")
	private BufferConfiguration bufferConfiguration = new BufferConfiguration();

	public CountersBufferConfiguration() {
	}

	public MongoServicesFactory getServicesFactory() {
		return servicesFactory;
	}

	public void setServicesFactory(MongoServicesFactory servicesFactory) {
		this.servicesFactory = servicesFactory;
	}

	public void setMongoFactory(MongoServicesFactory mongoFactory) {
		this.servicesFactory = mongoFactory;
	}

	public BufferConfiguration getBufferConfiguration() {
		return bufferConfiguration;
	}

	public void setBufferConfiguration(BufferConfiguration bufferConfiguration) {
		this.bufferConfiguration = bufferConfiguration;
	}
}
