package org.eyalgo.server.dropwizard.configuration_factories;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.eyalgo.buffer.BufferConfiguration;
import org.eyalgo.buffer.CountersBuffer;
import org.eyalgo.buffer.CountersCacheBuffer;
import org.eyalgo.counters.CountersRetriever;
import org.eyalgo.counters.CountersUpdater;
import org.eyalgo.counters.impl.mongo.MongoCountersRetriever;
import org.eyalgo.counters.impl.mongo.MongoCountersUpdater;
import org.eyalgo.server.dropwizard.CountersBufferConfiguration;
import org.eyalgo.server.dropwizard.core.CountersServices;
import org.eyalgo.server.dropwizard.core.ServicesFactory;
import org.eyalgo.server.dropwizard.health.MongoConnectionHealth;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.MongoClient;

public class MongoServicesFactory implements ServicesFactory {
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

	public MongoServicesFactory() {
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

	@Override
	public CountersServices build(Environment environment, CountersBufferConfiguration configuration) {
		try {
			MongoClient client = new MongoClient(getHost(), getPort());
			environment.lifecycle().manage(new Managed() { // TODO create a class instead of anonymous
						@Override
						public void start() {
						}

						@Override
						public void stop() {
							client.close();
						}
					});
			environment.healthChecks().register("mongo", new MongoConnectionHealth(client));
			return new MongoCountersServices(client, getDb(), configuration.getBufferConfiguration());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Problem building CountersServices", e);
		}
	}

	private final static class MongoCountersServices implements CountersServices {
		private final CountersRetriever countersRetriever;
		private final CountersBuffer countersBuffer;

		private MongoCountersServices(MongoClient client, String _db, BufferConfiguration bufferConfiguration) {
			Datastore datastore = new Morphia().createDatastore(client, _db);
			this.countersRetriever = new MongoCountersRetriever(datastore);
			CountersUpdater countersUpdater = new MongoCountersUpdater(datastore);
			this.countersBuffer = new CountersCacheBuffer(countersUpdater, bufferConfiguration);
			
		}

		@Override
		public CountersRetriever getCountersRetriever() {
			return countersRetriever;
		}

		@Override
		public CountersBuffer getCountersBuffer() {
			return countersBuffer;
		}
	}
}
