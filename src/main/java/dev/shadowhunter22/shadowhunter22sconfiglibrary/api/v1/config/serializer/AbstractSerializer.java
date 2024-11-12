//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.serializer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.autoconfig.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.autoconfig.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AbstractConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
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

	public <T extends ConfigData> void setValue(AbstractConfigManager manager, String key, Object value) {
		if (manager instanceof AutoConfigManager<?>) {
			((AutoConfigManager<T>) manager).getSerializer().setValue(((AutoConfigManager<?>) manager).getConfig(), key, value);
		} else if (manager.getSerializer() instanceof ConfigSerializer) {
			((ConfigManager) manager).getSerializer().setValue(key, value);
		}
	}

	protected abstract Logger logger();
}
