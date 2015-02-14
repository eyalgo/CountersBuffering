package org.eyalgo.counters;

import com.google.common.base.Objects;

public class TestableCounterable implements Counterable {
	private final String n1;
	private final String n2;

	public TestableCounterable(String n1, String n2) {
		this.n1 = n1;
		this.n2 = n2;
	}

	@Override
	public String counterKey() {
		return n1 + "::" + n2;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(n1, n2);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof TestableCounterable) {
			TestableCounterable that = (TestableCounterable) object;
			return Objects.equal(this.n1, that.n1) && Objects.equal(this.n2, that.n2);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return counterKey();
	}
}
