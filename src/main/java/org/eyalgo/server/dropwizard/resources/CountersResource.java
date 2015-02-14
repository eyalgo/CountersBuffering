package org.eyalgo.server.dropwizard.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.eyalgo.buffer.CountersBuffer;
import org.eyalgo.counters.CountersRetriever;
import org.eyalgo.server.dropwizard.api.ServiceCounter;

import com.codahale.metrics.annotation.Timed;

@Path("/counters")
public class CountersResource {

	private final CountersRetriever countersRetriever;
	private final CountersBuffer countersBuffer;

	public CountersResource(CountersRetriever countersRetriever, CountersBuffer countersBuffer) {
		this.countersRetriever = countersRetriever;
		this.countersBuffer = countersBuffer;
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Path("persisted")
	public Map<String, Integer> persistedCounters() {
		try {
			return countersRetriever.getAllCounters(ServiceCounter.class);
		} catch (Exception e) {
			throw new WebApplicationException(Status.SERVICE_UNAVAILABLE);
		}
	}

	@GET
	@Timed
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buffer")
	public Map<String, Integer> bufferCounters() {
		try {
			return countersBuffer.showBuffer();
		} catch (Exception e) {
			throw new WebApplicationException(Status.SERVICE_UNAVAILABLE);
		}
	}
}
