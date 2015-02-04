package org.eyalgo.counters.impl.mongo;

import org.bson.types.ObjectId;
import org.eyalgo.counters.CountersUpdater;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

public class MongoCountersUpdater extends CountersUpdater {
	private final MongoCounterUpdaterDao mongoCounterUpdaterDao;

	public MongoCountersUpdater(Datastore datastore) {
		this.mongoCounterUpdaterDao = new MongoCounterUpdaterDao(datastore);
	}

	@Override
	protected void increaseCounter(String key, int value) {
		Query<Counter> query = mongoCounterUpdaterDao.createQuery();
		query.criteria("id").equal(key);
		UpdateOperations<Counter> ops = mongoCounterUpdaterDao.getDs().createUpdateOperations(Counter.class).inc("count", value);
		mongoCounterUpdaterDao.getDs().update(query, ops, true);

	}

	private final static class MongoCounterUpdaterDao extends BasicDAO<Counter, ObjectId> {
		private MongoCounterUpdaterDao(Datastore ds) {
			super(Counter.class, ds);
		}
	}
}
