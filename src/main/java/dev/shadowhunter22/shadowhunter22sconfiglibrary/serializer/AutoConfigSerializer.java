//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.serializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;

import net.fabricmc.loader.api.FabricLoader;

import org.apache.commons.lang3.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

public class AutoConfigSerializer<T extends ConfigData> extends AbstractSerializer {
	private final Class<T> configClass;
	private final Config definition;
	private final Gson gson;

	private AutoConfigSerializer(Config definition, Class<T> configClass, Gson gson) {
		this.configClass = configClass;
		this.gson = gson;
		this.definition = definition;
	}

	public AutoConfigSerializer(Config definition, Class<T> configClass) {
		this(definition, configClass, new GsonBuilder().setPrettyPrinting().create());
	}

	protected Path getConfigPath() {
		return FabricLoader.getInstance().getConfigDir().resolve(this.getConfigFileDirectory() + "/" + this.getConfigFileName());
	}

	protected String getConfigFileDirectory() {
		return this.definition.name();
	}

	protected String getConfigFileName() {
		return this.definition.file().isEmpty() ? "options.json" : this.definition.file() + ".json";
	}

	public void serialize(T config) throws SerializationException {
		this.createDirectoryIfAbsent();
		this.writeToConfigFile(config);
	}

	public T deserialize() throws SerializationException {
		Path configPath = this.getConfigPath();

		if (Files.exists(this.getConfigPath())) {
			try (BufferedReader fileReader = Files.newBufferedReader(configPath)) {
				T object = this.gson.fromJson(fileReader, this.configClass);
				fileReader.close();

				return object;
			} catch (IOException e) {
				this.logger().warn("Unable to deserialize config file: {}", configPath);
				throw new SerializationException(e);
			}
		} else {
			T config = this.constructConfig();

			this.createDirectoryIfAbsent();
			this.writeToConfigFile(config);

			return config;
		}
	}

	public void setValue(T config, String key, Object value) {
		try {
			Field field = config.getClass().getDeclaredField(key);
			field.setAccessible(true);
			field.set(config, value);
		} catch (ReflectiveOperationException e) {
			this.logger().warn("Failed to set new value for config class: {}", config.getClass().getName());
			throw new RuntimeException(e);
		}
	}

	public Object getValue(T config, String key) {
		try {
			Field field = config.getClass().getDeclaredField(key);
			field.setAccessible(true);
			return field.get(config);
		} catch (ReflectiveOperationException e) {
			this.logger().warn("Failed to get for config class: {}", config.getClass().getName());
			throw new RuntimeException(e);
		}
	}

	public T constructConfig() {
		try {
			Constructor<T> constructor = this.configClass.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Logger logger() {
		return LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/AutoConfigSerializer");
	}

	private void writeToConfigFile(T config) {
		try (BufferedWriter fileWriter = Files.newBufferedWriter(this.getConfigPath())) {
			this.gson.toJson(config, fileWriter);
		} catch (IOException e) {
			this.logger().warn("Unable to serialize config file: {}", this.getConfigPath());
			throw new SerializationException(e);
		}
	}
}
