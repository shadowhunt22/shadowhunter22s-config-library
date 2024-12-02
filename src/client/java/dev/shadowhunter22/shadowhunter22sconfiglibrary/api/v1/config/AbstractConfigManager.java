//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.serializer.AbstractSerializer;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.serializer.AutoConfigSerializer;

public abstract class AbstractConfigManager {
	/**
	 * Get the {@code name} of the config class annotated with {@link Config}.
	 */
	public abstract String getDefinition();

	/**
	 * Serialize the config class into JSON and save it to the config directory.
	 *
	 * @see AutoConfigSerializer#serialize(ConfigData)
	 */
	public abstract void save();

	/**
	 * Deserialize the JSON file from the config directory and save the values to the config class.
	 *
	 * @see AutoConfigSerializer#deserialize()
	 */
	protected abstract void load();

	/**
	 * Update a config value and its option.
	 *
	 * @param key   the name of the field (case-sensitive)
	 * @param value the new value
	 */
	public abstract void updateValue(String key, Object value);

	public abstract AbstractSerializer getSerializer();
}
