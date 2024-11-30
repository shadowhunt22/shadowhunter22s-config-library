//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.ConfigEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.registry.GuiRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.ConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.ConfigScreenProvider;

import net.minecraft.client.gui.screen.Screen;

/**
 * This class is used to register configs.  To register a config, call {@link ConfigRegistry#register(Class)},
 * passing in the config class as a parameter.  This will automatically register a {@link ConfigScreenProvider} which
 * you can get the {@link ConfigScreen}.
 */
public class ConfigRegistry {
	private static final Map<Class<? extends ConfigData>, AbstractConfigManager> configs = new HashMap<>();

	/**
	 * Given a class containing config information, register a config file and return an
	 * {@link AutoConfigManager} to get the config class and its information.
	 *
	 * @param configClass the class to serialize to JSON.  If this value is null, a {@link NullPointerException} is
	 *                    thrown.
	 * @return a config manager that gives access to the config and all of its fields
	 */
	public static <T extends ConfigData> AutoConfigManager<T> register(Class<T> configClass) {
		Objects.requireNonNull(configClass);

		if (ConfigRegistry.isRegistered(configClass)) {
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
		if (!ConfigRegistry.isRegistered(configClass)) {
			throw new RuntimeException(String.format("Could not find config file '%s'. Was it registered?", configClass));
		}

		return (AutoConfigManager<T>) configs.get(configClass);
	}

	/**
	 * Get an automatically generated config screen by provided a config class and current screen.
	 *
	 * @param configClass   the config class that was registered
	 * @param currentScreen the current screen, obtained through {@code MinecraftClient.getInstance().currentScreen}.
	 * @return A {@link ConfigScreenProvider} that contains the screen that has been generated
	 */
	public static <T extends ConfigData> ConfigScreenProvider<T> getConfigScreen(Class<T> configClass, Screen currentScreen) {
		if (!ConfigRegistry.isRegistered(configClass)) {
			throw new RuntimeException(String.format("Could not find config file '%s'. Was it registered?", configClass));
		}

		return new ConfigScreenProvider<>(configClass, ConfigRegistry.getConfigManager(configClass), currentScreen);
	}

	public static <T extends ConfigData> String getDefinition(Class<T> configClass) {
		if (!ConfigRegistry.isRegistered(configClass)) {
			throw new RuntimeException(String.format("Could not find config file '%s'. Was it registered?", configClass));
		}

		return ConfigRegistry.getConfigManager(configClass).getDefinition();
	}

	public static boolean hasSectionAnnotation(Field field) {
		return field.isAnnotationPresent(ConfigEntry.Gui.Section.class);
	}

	public static boolean hasCategoryAnnotation(Field field) {
		return field.isAnnotationPresent(ConfigEntry.Gui.Category.class);
	}
}
