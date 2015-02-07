package org.eyalgo.server.dropwizard;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {

	private long id;

	@Length(max = 3)
	private String content;

	public Saying() {
		// Jackson deserialization
	}

	public Saying(long id, String content) {
		this.id = id;
		this.content = content;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}

}
