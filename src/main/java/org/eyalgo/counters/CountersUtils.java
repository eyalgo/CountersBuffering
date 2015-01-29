package org.eyalgo.counters;

public class CountersUtils {
	private static final String PREFIX_DELIMITER = "_";

	private CountersUtils() {
	}

	public static String counterPrefix(Class<? extends Counterable> clazz) {
		return clazz.getSimpleName() + PREFIX_DELIMITER;
	}

}
