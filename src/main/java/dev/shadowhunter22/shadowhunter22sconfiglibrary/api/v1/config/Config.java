//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class is used to register configs.  To register a config, call {@link Config#register(Class)},
 * passing in the config class as a parameter.
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/Config");

    private static final Map<Class<? extends ConfigData>, ConfigHolder<? extends ConfigData>> configs = new HashMap<>();

    /**
     * use
     * @param configClass the class to serialize to JSON.  If this value is null,
     * a {@link NullPointerException} is thrown.
     *
     * @return a config manager that gives access to the config and all of its fields
     */
    public static <T extends ConfigData> ConfigManager<T> register(Class<T> configClass) {
        Objects.requireNonNull(configClass);

        if (configs.containsKey(configClass)) {
            throw new RuntimeException(String.format("Config '%s' already registered", configClass));
        }

        ConfigManager<T> manager = new ConfigManager<>(configClass);

        configs.put(configClass, manager);

        return manager;
    }
}
