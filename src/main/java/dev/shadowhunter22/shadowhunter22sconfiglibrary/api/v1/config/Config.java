//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.ConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.ConfigScreenProvider;
import net.minecraft.client.gui.screen.Screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to register configs.  To register a config, call {@link Config#register(Class)},
 * passing in the config class as a parameter.  This will automatically register a {@link ConfigScreenProvider} which
 * you can get the {@link ConfigScreen}.
 */
public class Config {
    private static final Map<Class<? extends ConfigData>, AbstractConfigManager> configs = new HashMap<>();

    /**
     * Given a class containing config information, register a config file and return a
     * ConfigManager to get the config class and its information.
     *
     * @param configClass the class to serialize to JSON.  If this value is null,a {@link NullPointerException} is
     * thrown.
     *
     * @return a config manager that gives access to the config and all of its fields
     */
    public static <T extends ConfigData> AutoConfigManager<T> register(Class<T> configClass) {
        Objects.requireNonNull(configClass);

        if (configs.containsKey(configClass)) {
            throw new RuntimeException(String.format("Config '%s' already registered", configClass));
        }

        AutoConfigManager<T> manager = new AutoConfigManager<>(configClass);

        configs.put(configClass, manager);
        GuiRegistry.register(configClass, manager);
        manager.getConfig().afterLoad();

        return manager;
    }

    public static <T extends ConfigData> boolean isRegistered(Class<T> configClass) {
        return configs.containsKey(configClass);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ConfigData> AutoConfigManager<T> getConfigManager(Class<T> configClass) {
        if (!configs.containsKey(configClass)) {
            throw new RuntimeException(String.format("Could not find config file '%s'. Was it registered?", configClass));
        }

        return (AutoConfigManager<T>) configs.get(configClass);
    }

    public static <T extends ConfigData> ConfigScreenProvider<T> getConfigScreen(Class<T> configClass, Screen currentScreen) {
        if (!configs.containsKey(configClass)) {
            throw new RuntimeException(String.format("Could not find config file '%s'. Was it registered?", configClass));
        }

        return new ConfigScreenProvider<>(configClass, Config.getConfigManager(configClass), currentScreen);
    }
}
