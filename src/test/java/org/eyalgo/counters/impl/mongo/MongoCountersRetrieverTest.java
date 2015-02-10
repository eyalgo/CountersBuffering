package org.eyalgo.counters.impl.mongo;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.eyalgo.counters.TestableCounterable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

import com.google.common.collect.ImmutableMap;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoCountersRetrieverTest {
	private static final String DB_NAME = "test-embedded-mongo";
	private static final String COLLECTION_NAME = "counters";

	private static MongoClient mongo;
	private static DB db;

	private DBCollection collection;

	private static MongoCountersUpdater updater;
	private static MongoCountersRetriever retriever;

	@BeforeClass
	public static void createMongoClient() throws IOException {
		mongo = new MongoClient("localhost", 12345);
		updater = new MongoCountersUpdater(new Morphia().createDatastore(mongo, DB_NAME));
		retriever = new MongoCountersRetriever(new Morphia().createDatastore(mongo, DB_NAME));
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
	public void when_nothing_in_db_then_empty_map() {
		assertThat(retriever.getAllCounters(TestableCounterable.class).size(), is(0));
	}

	@Test
	public void when_data_in_db_then_should_get_it() {
		updater.increaseCounter(new TestableCounterable("k1", "p2"), 13);
		updater.increaseCounter(new TestableCounterable("k2", "p2"), 17);
		updater.increaseCounter(new TestableCounterable("k3", "p1"), 124);

		Map<String, Integer> expectedCounters = ImmutableMap.<String, Integer> builder().put("k1::p2", 13).put("k2::p2", 17).put("k3::p1", 124).build();
		Map<String, Integer> allCounters = retriever.getAllCounters(TestableCounterable.class);

		assertThat(allCounters, equalTo(expectedCounters));
	}

}
