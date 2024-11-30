//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration;

import java.util.HashMap;
import java.util.Map;

public class EnumSpecification {
	Map<String, String> keyValuePairs = new HashMap<>();
	Class<? extends Enum<?>> enumClass;

	public <E extends Enum<E>> void enumClass(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	public <E extends Enum<E>> void add(String key1, E key2) {
		this.keyValuePairs.put(key1, key2.name());
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

	public void add(Map<String, String> map) {
		this.keyValuePairs.putAll(map);
	}

	@SuppressWarnings("unchecked")
	protected <E extends Enum<E>> Value<E> convert(String key) {
		return new Value<>((Class<E>) this.enumClass, this.keyValuePairs.get(key));
	}

	protected void validate() {
		if (this.enumClass == null) {
			throw new IllegalStateException("The value of 'enumClass' cannot be null! Add enum class by calling EnumMapper#enumClass!");
		}
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
