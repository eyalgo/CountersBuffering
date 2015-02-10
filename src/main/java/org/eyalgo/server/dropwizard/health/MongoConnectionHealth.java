package org.eyalgo.server.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;

public class MongoConnectionHealth extends HealthCheck {
	private final MongoClient mongo;

	public MongoConnectionHealth(MongoClient mongo) {
		this.mongo = mongo;
	}

	@Override
	protected Result check() throws Exception {
		try {
			mongo.getDatabaseNames();
			return Result.healthy();
		} catch (Exception e) {
			return Result.unhealthy("Cannot connect to " + mongo.getAllAddress());
		}
	}
}
