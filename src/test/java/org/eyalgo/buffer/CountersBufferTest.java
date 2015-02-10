package org.eyalgo.buffer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.eyalgo.counters.AbstractCountersUpdater;
import org.eyalgo.counters.TestableCounterable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CountersBufferTest {
	@Mock
	private AbstractCountersUpdater countersUpdater;
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void when_items_not_in_threshold_then_updater_not_set() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 10);
		CountersBuffer buffer = new CountersBuffer(countersUpdater, cacheConfiguration);
		buffer.inc(new TestableCounterable("k1", "k2"));
		verifyZeroInteractions(countersUpdater);
	}

	@Test
	public void when_items_different_keys_not_in_threshold_then_updater_not_set() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 4);
		CountersBuffer buffer = new CountersBuffer(countersUpdater, cacheConfiguration);
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k3"));
		buffer.inc(new TestableCounterable("k1", "k3"));
		buffer.inc(new TestableCounterable("k1", "k4"));
		verifyZeroInteractions(countersUpdater);
	}
	
	@Test
	public void when_items_pass_threashold_then_calling_updater() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 4);
		CountersBuffer buffer = new CountersBuffer(countersUpdater, cacheConfiguration);
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k2"));
		buffer.inc(new TestableCounterable("k1", "k2"));
		verify(countersUpdater).increaseCounter(new TestableCounterable("k1", "k2"), 4);
	}
}
