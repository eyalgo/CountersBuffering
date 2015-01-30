package org.eyalog.counters.mongo;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoCountersUpdaterTest {
	private static final String DB_NAME = "pretech_embedded1";

	private static MongoClient mongo;

	@BeforeClass
	public static void createMongoClient() throws IOException {
		mongo = new MongoClient("localhost", 12345);
	}

	@AfterClass
	public static void closeMongoClient() throws Exception {
		mongo.close();
	}
	
	@Test
	public void foo() {
		DB db = mongo.getDB(DB_NAME);
		DBCollection collection = db.getCollection("pretechEmbeddedCol");
		BasicDBObject dbObject = new BasicDBObject("k1", "v1");
		collection.insert(dbObject);
		
		assertThat(collection.count(), is(1L));
	}
}
