package org.eyalgo.server.dropwizard.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eyalgo.buffer.CountersBuffer;
import org.eyalgo.server.dropwizard.api.ServiceCounter;

@Path("/incCounter/")
public class IncreaseCounterResource {

	private final CountersBuffer countersBufferIncrease;

	public IncreaseCounterResource(CountersBuffer countersBufferIncrease) {
		this.countersBufferIncrease = countersBufferIncrease;
	}

	@Consumes(value = MediaType.APPLICATION_JSON)
	@PUT
	public Response increaseCounter(ServiceCounter serviceCounter) {
		countersBufferIncrease.increase(serviceCounter);
		return Response.noContent().build();
	}
}
