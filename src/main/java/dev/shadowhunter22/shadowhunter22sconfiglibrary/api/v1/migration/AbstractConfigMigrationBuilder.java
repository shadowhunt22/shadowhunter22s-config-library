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
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConfigMigrationBuilder<T extends ConfigData> {
	public static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/AbstractConfigMigration");

	protected ConfigMigration<T> migration;

	protected abstract void build();

	public void migrate() {
		final Instant started = Instant.now();

		LOGGER.debug("Building migration specification");
		this.build();

		LOGGER.debug("Migrating config files...");
		boolean migrated = this.migration.migrate();

		if (migrated) {
			this.updateOptionsAfterMigration();
			LOGGER.debug("Updated options after migration");
			LOGGER.debug("Config migration for {} took {}ms.", this.migration.config.getClass().getSimpleName(), Duration.between(started, Instant.now()).toMillis());
			this.migration.config.afterMigration();
		} else {
			LOGGER.debug("Did not update options after migration");
		}
	}

	private void updateOptionsAfterMigration() {
		AutoConfigManager<T> manager = (AutoConfigManager<T>) Config.getConfigManager(this.migration.config.getClass());
		LinkedHashMap<String, ConfigOption<?>> options = GuiRegistry.getOptions(this.migration.config.getClass());

		options.forEach((key, option) -> {
			Object value = manager.getSerializer().getValue(this.migration.config, key);
			option.setValue(value);
		});
	}
}
