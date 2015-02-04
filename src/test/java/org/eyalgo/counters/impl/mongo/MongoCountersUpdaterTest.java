package org.eyalgo.counters.impl.mongo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.eyalgo.counters.TestableCounterable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoCountersUpdaterTest {
	private static final String DB_NAME = "test-embedded-mongo";
	private static final String COLLECTION_NAME = "counters";

	private static MongoClient mongo;
	private static DB db;

	private DBCollection collection;

	private static MongoCountersUpdater updater;

	@BeforeClass
	public static void createMongoClient() throws IOException {
		mongo = new MongoClient("localhost", 12345);
		updater = new MongoCountersUpdater(new Morphia().createDatastore(mongo, DB_NAME));
		db = mongo.getDB(DB_NAME);
	}

	@AfterClass
	public static void closeMongoClient() throws Exception {
		mongo.close();
	}

	@Before
	public void setupCollection() {
		collection = db.getCollection(COLLECTION_NAME);
		collection.drop();
	}

	@After
	public void cleanup() {
		collection.drop();
	}

	@Test
	public void when_collection_does_not_have_key_then_intializing_with_count() {
		updater.increaseCounter(new TestableCounterable("k1", "p2"), 13);

		collection = db.getCollection(COLLECTION_NAME);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", "TestableCounterable_k1::p2");

		DBCursor cursor = collection.find(searchQuery);
		Integer count = -1;
		int numberInCursor = 0;

		while (cursor.hasNext()) {
			numberInCursor++;
			DBObject next = cursor.next();
			count = (Integer) next.get("count");
		}
		assertThat("should find one record", numberInCursor, is(1));
		assertThat("count need to be the initial value", count, is(13));
	}

	@Test
	public void when_collection_has_counters_then_should_increase_amount() {
		updater.increaseCounter(new TestableCounterable("k1", "p2"), 13);
		updater.increaseCounter(new TestableCounterable("k1", "p2"), 25);

		collection = db.getCollection(COLLECTION_NAME);
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("_id", "TestableCounterable_k1::p2");

		DBCursor cursor = collection.find(searchQuery);
		Integer count = -1;
		int numberInCursor = 0;

		while (cursor.hasNext()) {
			numberInCursor++;
			DBObject next = cursor.next();
			count = (Integer) next.get("count");
		}
		assertThat("should find one record", numberInCursor, is(1));
		assertThat("count need to be sum of initial and incease", count, is(38));
	}
}
