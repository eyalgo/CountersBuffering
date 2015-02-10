package org.eyalgo.server.dropwizard.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eyalgo.counters.impl.mongo.MongoCountersUpdater;
import org.eyalgo.server.dropwizard.api.ServiceCounter;

@Path("/incCounter/")
public class IncreaseCounterResource {

	private final MongoCountersUpdater mongoCountersUpdater;

	public IncreaseCounterResource(MongoCountersUpdater mongoCountersUpdater) {
		this.mongoCountersUpdater = mongoCountersUpdater;
	}

	@Consumes(value = MediaType.APPLICATION_JSON)
	@PUT
	public Response increaseCounter(ServiceCounter serviceCounter) {
		mongoCountersUpdater.increaseCounter(serviceCounter, 1);
		return Response.noContent().build();
	}
}
