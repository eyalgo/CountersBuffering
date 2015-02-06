package org.eyalgo.counters.impl.mongo;

import java.util.List;

import org.eyalgo.counters.CountersRetriever;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public class MongoCountersRetriever extends CountersRetriever {
	private final MongoCountersDao dao;

	public MongoCountersRetriever(Datastore datastore) {
		this.dao = new MongoCountersDao(datastore);
	}

	@Override
	protected List<Counter> counters() {
		Query<Counter> find = dao.getDs().find(Counter.class);
		return find.asList();
	}

}
