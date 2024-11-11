//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.HashMap;

@ApiStatus.Internal
public class ConfigScreenProvider<T extends ConfigData> {
    private final Class<T> configClass;
    private final ConfigManager<T> configManager;
    private final Screen currentScreen;

	public ConfigScreenProvider(Class<T> configClass, ConfigManager<T> configManager, Screen currentScreen) {
        this.configClass = configClass;
        this.configManager = configManager;
        this.currentScreen = currentScreen;
    }

    public Screen get() {
        return new ConfigScreen<>(this.configManager, GuiRegistry.getOptions(this.configClass), this.currentScreen);
    }
}
