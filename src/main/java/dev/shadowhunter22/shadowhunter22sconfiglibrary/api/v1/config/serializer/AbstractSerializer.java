//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.serializer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AbstractConfigManager;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;

public abstract class AbstractSerializer {
	protected abstract Path getConfigPath();
	protected abstract String getConfigFileDirectory();
	protected abstract String getConfigFileName();

	protected void createDirectoryIfAbsent() {
		if (!Files.exists(this.getConfigPath())) {
			String configFileDirectory = this.getConfigFileDirectory();

			this.logger().warn("Unable to find a config file for {}/{}.  Creating...", configFileDirectory, this.getConfigFileName());

			File directory = new File(FabricLoader.getInstance().getConfigDir() + "/" + configFileDirectory);

			if (!directory.exists()) {
				boolean created = directory.mkdir();

				if (created) {
					this.logger().info("Successfully created a config directory for {}.", configFileDirectory);
				}
			}
		}
	}

	public void setValue(AbstractConfigManager manager, String key, Object value) {
		if (manager instanceof AutoConfigManager<?> autoConfigManager) {
			this.setValue(autoConfigManager, key, value);
		}
	}

	private <T extends ConfigData> void setValue(AutoConfigManager<T> manager, String key, Object value) {
		manager.getSerializer().setValue(manager.getConfig(), key, value);
	}

	protected abstract Logger logger();
}
