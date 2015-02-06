package org.eyalgo.counters.impl.mongo;

import org.eyalgo.counters.CountersUpdater;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class MongoCountersUpdater extends CountersUpdater {
	private final MongoCountersDao dao;

	public MongoCountersUpdater(Datastore datastore) {
		this.dao = new MongoCountersDao(datastore);
	}

	@Override
	protected void increaseCounter(String key, int value) {
		Query<Counter> query = dao.createQuery();
		query.criteria("id").equal(key);
		UpdateOperations<Counter> ops = dao.getDs().createUpdateOperations(Counter.class).inc("count", value);
		dao.getDs().update(query, ops, true);

	}
}
