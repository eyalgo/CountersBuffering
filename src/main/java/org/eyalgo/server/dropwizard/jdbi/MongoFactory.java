package org.eyalgo.server.dropwizard.jdbi;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.eyalgo.counters.CountersRetriever;
import org.eyalgo.counters.impl.mongo.MongoCountersRetriever;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;

public class MongoFactory {
	@NotEmpty
	private String host = "localhost";

	@Min(1)
	@Max(65535)
	@JsonProperty
	private int port = 27017;

	@JsonProperty
	@NotEmpty
	private String db = "meta";

	@JsonProperty
	@NotEmpty
	private String dbCollection = "counters";

	public MongoFactory() {
	}

	@JsonProperty
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@JsonProperty
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@JsonProperty
	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	@JsonProperty
	public String getDbCollection() {
		return dbCollection;
	}

	public void setDbCollection(String dbCollection) {
		this.dbCollection = dbCollection;
	}

	public MongoClient build(Environment environment) throws UnknownHostException {
		MongoClient client = new MongoClient(getHost(), getPort());
		environment.lifecycle().manage(new Managed() {
			@Override
			public void start() {
			}

			@Override
			public void stop() {
				System.out.println("Closing mongo client");
				client.close();
			}
		});
		return client;
	}

	public CountersRetriever createCountersRetriever(MongoClient client) {
		return new MongoCountersRetriever(new Morphia().createDatastore(client, getDb()));
	}
}
