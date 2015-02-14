package org.eyalgo.server.dropwizard.api;

import org.eyalgo.counters.Counterable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

public class ServiceCounter implements Counterable {
	private final String majorKey;
	private final String minorKey;

	@JsonCreator
	public ServiceCounter(@JsonProperty("majorKey")String majorKey, @JsonProperty("minorKey")String minorKey) {
		this.majorKey = majorKey;
		this.minorKey = minorKey;
	}

	@JsonProperty("majorKey")
	public String getMajorKey() {
		return majorKey;
	}

	@JsonProperty("minorKey")
	public String getMinorKey() {
		return minorKey;
	}

	@Override
	public String counterKey() {
		return majorKey + "::" + minorKey;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(majorKey, minorKey);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ServiceCounter) {
			ServiceCounter that = (ServiceCounter) object;
			return Objects.equal(this.majorKey, that.majorKey) && Objects.equal(this.minorKey, that.minorKey);
		}
		return false;
	}

	@Override
	public String toString() {
		return counterKey();
	}

}
