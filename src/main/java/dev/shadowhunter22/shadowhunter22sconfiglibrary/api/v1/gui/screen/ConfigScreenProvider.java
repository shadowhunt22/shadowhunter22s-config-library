//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import net.minecraft.client.gui.screen.Screen;

/**
 * Given a {@code configClass}, {@code manager}, and {@code currentScreen}, create a new {@link ConfigScreen} that can be retrieved to view and
 * edit config options.
 */
public class ConfigScreenProvider<T extends ConfigData> {
    private final Class<T> configClass;
    private final AutoConfigManager<T> manager;
    private final Screen currentScreen;

	public ConfigScreenProvider(Class<T> configClass, AutoConfigManager<T> manager, Screen currentScreen) {
        this.configClass = configClass;
        this.manager = manager;
        this.currentScreen = currentScreen;
    }

    public Screen get() {
        return new ConfigScreen<>(this.manager, GuiRegistry.getOptions(this.configClass), this.currentScreen);
    }
}
