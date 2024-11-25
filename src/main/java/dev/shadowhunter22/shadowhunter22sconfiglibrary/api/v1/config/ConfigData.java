//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;

public interface ConfigData {
	/**
	 * This method is called after a mod's config file has been migrated from {@code config_a.json} to {@code config_b.json}.
	 * This method will only be called once unless {@code {definition}/.migration.json} does not exist or does not have a key-value pair of
	 * {@code "migrated": true}.
	 */
	default void afterMigration() {
	}

	/**
	 * This method is called after a mod's config file has an {@code AutoConfigManager} and has a {@code GuiRegistry} registry.
	 */
	default void afterLoad() {
	}

	/**
	 * This method is called after a save operation takes place, usually when an option has been edited by a user.
	 */
	default void afterSave() {
	}

	/**
	 * The purpose of the method is to "listen" for a change to a specific config option and then perform an operation.
	 * <p>
	 * Example usage:
	 *
	 * <pre>{@code
	 * @Config(name = "config-class")
	 * public class ConfigClass implements ConfigData {
	 *     public SomeEnum someEnum = SomeEnum.VALUE;
	 *
	 *     @Override
	 *     public <T extends ConfigData> void afterChange(Class<T> config, String key) {
	 *         if (config == ConfigClass.class && key.equals("someEnum")) {
	 *             // code
	 *         }
	 *     }
	 * }
	 * }</pre>
	 *
	 * @param config the config class that is emitting a change
	 * @param key the name of the field that is being changed
	 */
	default <T extends ConfigData> void afterChange(Class<T> config, String key) {
	}

	/**
	 * This method is called when a screen extending {@link AbstractConfigScreen}
	 * has been closed.
	 */
	default void afterScreenClose() {
	}
}
