package org.eyalgo.counters;

import org.eyalgo.counters.Counterable;

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

}
