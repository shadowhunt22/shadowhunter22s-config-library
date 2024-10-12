//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.IntegerConfigOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class GuiRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/GuiRegistry");

    private static final LinkedHashMap<Class<? extends ConfigData>, LinkedHashMap<Field, BaseConfigOption<?>>> registry = new LinkedHashMap<>();

    public static <T extends ConfigData> void register(Class<T> configClass, ConfigManager<T> configManager) {
        if (registry.containsKey(configClass)) {
            throw new RuntimeException(String.format("Gui for config '%s' already registered", configClass));
        }

        registry.put(configClass, populateOptions(configClass, configManager));
    }

    public static <T extends ConfigData> LinkedHashMap<Field, BaseConfigOption<?>> getOptions(Class<T> configClass) {
        if (!registry.containsKey(configClass)) {
            throw new RuntimeException(String.format("Could not find a registered gui for '%s'. Was it registered?", configClass));
        }

        return registry.get(configClass);
    }

    private static <T extends ConfigData> LinkedHashMap<Field, BaseConfigOption<?>> populateOptions(Class<T> configClass, ConfigManager<T> configManager) {
        T config = configManager.getConfig();
        T defaultConfig = configManager.getSerializer().constructConfig();

        LinkedHashMap<Field, BaseConfigOption<?>> options = new LinkedHashMap<>();

        try {
            for (Field field : configClass.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.getType() == Integer.TYPE) {
                    options.putAll(getIntegerOption(configClass, config, defaultConfig, field));
                }

                if (field.getType() == Boolean.TYPE) {
                    options.putAll(getBooleanOption(configClass, config, defaultConfig, field));
                }

                if (field.getType().isEnum()) {
                    options.putAll(getEnumOption(configClass, config, defaultConfig, field));
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.warn("Unable to create populate options for '{}' config.", configClass.getDeclaredAnnotation(Config.class).name());
            LOGGER.warn("{}", e.getMessage());
        }

        return new LinkedHashMap<>(options);
    }

    private static <T extends ConfigData> Map<Field, BooleanConfigOption<Boolean>> getBooleanOption(Class<T> configClass, T config, T defaultConfig, Field field) throws IllegalAccessException {
        String key = field.getName();
        boolean value = field.getBoolean(config);
        boolean defaultValue = field.getBoolean(defaultConfig);

        return Map.of(field, new BooleanConfigOption<>(configClass.getDeclaredAnnotation(Config.class), key, value, defaultValue));
    }

    private static <T extends ConfigData> Map<Field, IntegerConfigOption<Integer>> getIntegerOption(Class<T> configClass, T config, T defaultConfig, Field field) throws IllegalAccessException {
        String key = field.getName();

        int value = field.getInt(config);
        int defaultValue = field.getInt(defaultConfig);
        int min = field.getDeclaredAnnotation(ConfigEntry.Integer.class).min();
        int max = field.getDeclaredAnnotation(ConfigEntry.Integer.class).max();

        return Map.of(field, new IntegerConfigOption<>(configClass.getDeclaredAnnotation(Config.class), key, min, max, value, defaultValue));
    }

    @SuppressWarnings("unchecked")
    private static <T extends ConfigData, E extends Enum<E>> Map<Field, EnumConfigOption<E>> getEnumOption(Class<T> configClass, T config, T defaultConfig, Field field) throws IllegalAccessException {
        String key = field.getName();
        E[] values = ((Enum<E>) field.get(defaultConfig)).getDeclaringClass().getEnumConstants();
        E value = (E) field.get(config);
        E defaultValue = (E) field.get(defaultConfig);

        return Map.of(field, new EnumConfigOption<>(configClass.getDeclaredAnnotation(Config.class), key, values, value, defaultValue));
    }
}
