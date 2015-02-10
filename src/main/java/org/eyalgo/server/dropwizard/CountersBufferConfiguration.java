package org.eyalgo.server.dropwizard;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountersBufferConfiguration extends Configuration {
	private MongoClientFactory mongoConfiguration = new MongoClientFactory();

	public CountersBufferConfiguration() {
	}

	@JsonProperty("mongo")
	public MongoClientFactory getMongoConfiguration() {
		return mongoConfiguration;
	}

	public void setMongoConfiguration(MongoClientFactory mongoConfiguration) {
		this.mongoConfiguration = mongoConfiguration;
	}
}
