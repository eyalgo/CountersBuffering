package org.eyalgo.server.dropwizard;

import org.eyalgo.server.dropwizard.jdbi.MongoFactory;

import io.dropwizard.Configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountersBufferConfiguration extends Configuration {
	private MongoFactory mongoFactory = new MongoFactory();

	public CountersBufferConfiguration() {
	}

	@JsonProperty("mongo")
	public MongoFactory getMongoFactory() {
		return mongoFactory;
	}

	public void setMongoFactory(MongoFactory mongoFactory) {
		this.mongoFactory = mongoFactory;
	}
}
