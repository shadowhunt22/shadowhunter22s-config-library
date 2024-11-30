//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigMigrationBuilder<T extends ConfigData> {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/AbstractConfigMigration");

	protected abstract ConfigMigration<T> build();

	public void migrate() {
		final Instant started = Instant.now();

		LOGGER.debug("Building migration specification");
		ConfigMigration<T> configMigration = this.build();

		LOGGER.debug("Migrating config files...");
		boolean migrated = configMigration.migrate();

		if (migrated) {
			this.updateOptionsAfterMigration(configMigration.config);
			LOGGER.debug("Updated options after migration");
			LOGGER.debug("Config migration for {} took {}ms.", configMigration.config.getClass().getSimpleName(), Duration.between(started, Instant.now()).toMillis());
			configMigration.config.afterMigration();
		} else {
			LOGGER.debug("Did not update options after migration");
		}
	}

	private void updateOptionsAfterMigration(T config) {
		AutoConfigManager<T> manager = (AutoConfigManager<T>) ConfigRegistry.getConfigManager(config.getClass());
		LinkedHashMap<String, ConfigOption<?>> options = GuiRegistry.getOptions(config.getClass());

		options.forEach((key, option) -> {
			Object value = manager.getSerializer().getValue(config, key);
			option.setValue(value);
		});
	}
}
