//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import net.minecraft.client.gui.screen.Screen;

public class ConfigScreenProvider<T extends ConfigData> {
    private final Class<T> configClass;
    private final AutoConfigManager<T> manager;
    private final Screen currentScreen;

	public ConfigScreenProvider(Class<T> configClass, AutoConfigManager<T> configManager, Screen currentScreen) {
        this.configClass = configClass;
        this.manager = configManager;
        this.currentScreen = currentScreen;
    }

    public Screen get() {
        return new ConfigScreen<>(this.manager, GuiRegistry.getOptions(this.configClass), this.currentScreen);
    }
}
