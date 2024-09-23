//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.BooleanConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.EnumConfigOption;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.type.IntegerConfigOption;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

@ApiStatus.Internal
public class ConfigScreenProvider<T extends ConfigData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/ConfigScreenProvider");

    private final Class<T> configClass;
    private final ConfigManager<T> configManager;
    private final Screen currentScreen;

    // private final ArrayList<BaseConfigOption<?>> options = new ArrayList<>();
    private final HashMap<Field, BaseConfigOption<?>> options = new HashMap<>();

    public ConfigScreenProvider(Class<T> configClass, ConfigManager<T> configManager, Screen currentScreen) {
        this.configClass = configClass;
        this.configManager = configManager;
        this.currentScreen = currentScreen;
    }

    public Screen get() {
        this.populateOptions();

        return new ConfigScreen<>(this.configManager, this.options, this.currentScreen);
    }

    @SuppressWarnings("unchecked")
    private <E extends Enum<E>> void populateOptions() {
        T defaultConfig = this.configManager.getSerializer().constructConfig();
        T config = this.configManager.getConfig();

        try {
            for (Field field : this.configClass.getDeclaredFields()) {
                field.setAccessible(true);

                if (field.getType() == Integer.TYPE) {
                    String key = field.getName();
                    int value = field.getInt(config);
                    int defaultValue = field.getInt(defaultConfig);

                    this.options.put(field, new IntegerConfigOption<>(this.configClass.getDeclaredAnnotation(Config.class), key, 0, 0, value, defaultValue));
                }

                if (field.getType() == Boolean.TYPE) {
                    String key = field.getName();
                    boolean value = field.getBoolean(config);
                    boolean defaultValue = field.getBoolean(defaultConfig);

                    this.options.put(field, new BooleanConfigOption<>(this.configClass.getDeclaredAnnotation(Config.class), key, value, defaultValue));
                }

                if (field.getType().isEnum()) {
                    String key = field.getName();
                    E[] values = ((Enum<E>) field.get(defaultConfig)).getDeclaringClass().getEnumConstants();
                    E value = (E) field.get(config);
                    E defaultValue = (E) field.get(defaultConfig);

                    options.put(field, new EnumConfigOption<>(this.configClass.getDeclaredAnnotation(Config.class), key, values, value, defaultValue));
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.warn("Unable to create ConfigScreenProvider for '{}' config.", this.configClass.getDeclaredAnnotation(Config.class).name());
            LOGGER.warn("{}", e.getMessage());
        }
    }
}
