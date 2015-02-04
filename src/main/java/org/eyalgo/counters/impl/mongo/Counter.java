package org.eyalgo.counters.impl.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity(value = "counters", noClassnameStored = true)
public class Counter {
	@Id
	private final String id;

	private final int count;

	public Counter() {
		// for Morphia
		this("");
	}

	public Counter(String id) {
		this(id, 0);
	}

	public Counter(String id, int count) {
		this.id = id;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public int getCount() {
		return count;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, count);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Counter) {
			Counter that = (Counter) object;
			return Objects.equal(this.id, that.id) && Objects.equal(this.count, that.count);
		}
		return false;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("ID", id).add("Count", count).toString();
	}
}
