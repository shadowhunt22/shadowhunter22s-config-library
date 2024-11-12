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
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.IntegerConfigOption;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

@ApiStatus.Internal
public class GuiRegistry {
    // TODO make the gui not dependent on a field to modify a value; use key-value pairs

    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/GuiRegistry");

    private static final LinkedHashMap<Class<? extends ConfigData>, LinkedHashMap<Field, ConfigOption<?>>> registry = new LinkedHashMap<>();

    public static <T extends ConfigData> void register(Class<T> configClass, ConfigManager<T> configManager) {
        if (!dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.Config.isRegistered(configClass)) {
            throw new RuntimeException(String.format("GuiRegistry attempted to register a missing config file: '%s'. Was it registered with Config.register()?", configClass));
        }

        if (registry.containsKey(configClass)) {
            throw new RuntimeException(String.format("Gui for config '%s' already registered", configClass));
        }

        registry.put(configClass, populateOptions(configClass, configManager));
    }

    public static <T extends ConfigData> boolean isRegistered(Class<T> configClass) {
        return registry.containsKey(configClass);
    }

    public static <T extends ConfigData> LinkedHashMap<Field, ConfigOption<?>> getOptions(Class<T> configClass) {
        if (!registry.containsKey(configClass)) {
            throw new RuntimeException(String.format("Could not find a registered gui for '%s'. Was it registered?", configClass));
        }

        return registry.get(configClass);
    }

    private static <T extends ConfigData> LinkedHashMap<Field, ConfigOption<?>> populateOptions(Class<T> configClass, ConfigManager<T> configManager) {
        T config = configManager.getConfig();
        T defaultConfig = configManager.getSerializer().constructConfig();

        LinkedHashMap<Field, ConfigOption<?>> options = new LinkedHashMap<>();

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
            LOGGER.error("Unable to create populate options for '{}' config.", configClass.getDeclaredAnnotation(Config.class).name());
            LOGGER.error("{}", e.getMessage());
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

        int min, max;

        try {
            min = field.getDeclaredAnnotation(ConfigEntry.Integer.class).min();
            max = field.getDeclaredAnnotation(ConfigEntry.Integer.class).max();
        } catch (Exception e) {
            LOGGER.error("Unable to get minimum and maximum values for '{}' field because the '{}' annotation was not present.", field.getName(), ConfigEntry.Integer.class.getName());

            throw new NullPointerException(
                    String.format(
                            "Unable to get minimum and maximum values for '%s' field because the '%s' annotation was not present.",
                            field.getName(),
                            ConfigEntry.Integer.class.getName()
                    )
            );
        }

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
