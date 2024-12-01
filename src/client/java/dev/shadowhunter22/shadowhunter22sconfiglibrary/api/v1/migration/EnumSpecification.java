//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration;

import java.util.HashMap;
import java.util.Map;

public class EnumSpecification {
	private final Map<String, String> keyValuePairs = new HashMap<>();
	private final Class<? extends Enum<?>> enumClass;

	public <E extends Enum<E>> EnumSpecification(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	/**
	 * Using this method means there is a 1:1 translation of the enum value from {@code config_a.json} to {@code config_b.json}.
	 */
	public void add(String key) {
		this.keyValuePairs.put(key, key);
	}

	public void add(String key1, String key2) {
		this.keyValuePairs.put(key1, key2);
	}

	public <E extends Enum<E>> void add(String key1, E key2) {
		this.keyValuePairs.put(key1, key2.name());
	}

	public <E extends Enum<E>> void add(E key1, E key2) {
		this.keyValuePairs.put(key1.name(), key2.name());
	}

	@SuppressWarnings("unchecked")
	protected <E extends Enum<E>> Value<E> convert(String key) {
		return new Value<>((Class<E>) this.enumClass, this.keyValuePairs.get(key));
	}

	protected void validate() {
		if (this.enumClass == null) {
			throw new IllegalStateException("The value of 'enumClass' cannot be null! Add enum class by calling EnumMapper#enumClass!");
		}

		this.keyValuePairs.forEach((key1, key2) -> {
			if (key1.isEmpty()) {
				throw new IllegalStateException("The value of 'key1' cannot be empty!");
			}

			if (key2.isEmpty()) {
				throw new IllegalStateException("The value of 'key2' cannot be empty!");
			}
		});
	}

	@FunctionalInterface
	public interface Mapper {
		EnumSpecification specification(EnumSpecification mapper);
	}

	public static class Value<E extends Enum<E>> {
		final String value;
		final Class<E> enumClass;

		public Value(Class<E> enumClass, String value) {
			this.enumClass = enumClass;
			this.value = value;
		}
	}
}
