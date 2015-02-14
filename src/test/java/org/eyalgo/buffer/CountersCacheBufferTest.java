package org.eyalgo.buffer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Map;

import org.eyalgo.counters.AbstractCountersUpdater;
import org.eyalgo.counters.TestableCounterable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;

@RunWith(MockitoJUnitRunner.class)
public class CountersCacheBufferTest {
	@Mock
	private AbstractCountersUpdater countersUpdater;
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void when_items_not_in_threshold_then_updater_not_set() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 10);
		CountersBuffer buffer = new CountersCacheBuffer(countersUpdater, cacheConfiguration);
		buffer.increase(new TestableCounterable("k1", "k2"));
		verifyZeroInteractions(countersUpdater);
	}

	@Test
	public void when_items_different_keys_not_in_threshold_then_updater_not_set() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 4);
		CountersBuffer buffer = new CountersCacheBuffer(countersUpdater, cacheConfiguration);
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k3"));
		buffer.increase(new TestableCounterable("k1", "k3"));
		buffer.increase(new TestableCounterable("k1", "k4"));
		verifyZeroInteractions(countersUpdater);
	}
	
	@Test
	public void when_items_pass_threashold_then_calling_updater() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 4);
		CountersBuffer buffer = new CountersCacheBuffer(countersUpdater, cacheConfiguration);
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		verify(countersUpdater).increaseCounter(new TestableCounterable("k1", "k2"), 4);
	}

	@Test
	public void when_putting_items_then_should_be_in_buffer() {
		BufferConfiguration cacheConfiguration = new BufferConfiguration(100000, 1000, 100000, 4);
		CountersCacheBuffer buffer = new CountersCacheBuffer(countersUpdater, cacheConfiguration);
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k1", "k2"));
		buffer.increase(new TestableCounterable("k6", "k3"));

		Map<String, Integer> expected = ImmutableMap.<String, Integer>builder().put("k1::k2", 2).put("k6::k3", 1).build();
		assertThat(buffer.showBuffer(), equalTo(expected));
		
	}
}
