package org.eyalgo.counters.impl.mongo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

final class MongoCountersDao extends BasicDAO<Counter, ObjectId> {

	MongoCountersDao(Datastore ds) {
		super(Counter.class, ds);
	}
}
