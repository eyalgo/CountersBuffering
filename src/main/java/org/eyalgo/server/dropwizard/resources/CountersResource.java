package org.eyalgo.server.dropwizard.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eyalgo.counters.Counterable;
import org.eyalgo.counters.CountersRetriever;

import com.codahale.metrics.annotation.Timed;

@Path("/counters")
public class CountersResource {

	private final CountersRetriever countersRetriever;
	private final Class<? extends Counterable> clazz;

	public CountersResource(CountersRetriever countersRetriever, Class<? extends Counterable> clazz) {
		this.countersRetriever = countersRetriever;
		this.clazz = clazz;
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Integer> counters() {
		try {
			return countersRetriever.getAllCounters(clazz);
		} catch (Exception e) {
			throw new WebApplicationException(Status.SERVICE_UNAVAILABLE);
		}
	}

}
