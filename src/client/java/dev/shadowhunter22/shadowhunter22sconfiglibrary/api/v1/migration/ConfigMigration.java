//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.migration;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;

import net.fabricmc.loader.api.FabricLoader;

import org.apache.commons.lang3.SerializationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class ConfigMigration<T extends ConfigData> {
	protected final T config;

	private final Path oldConfig;
	private final Path newConfig;
	private final Path migrationFile;

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final HashMap<String, Object> configValues = new HashMap<>();
	private final HashMap<String, EnumSpecification.Value<? extends Enum<?>>> configEnumValues = new HashMap<>();
	private boolean hasMigrated = false;

	public ConfigMigration(T config, String oldConfigLocation, String newConfigLocation, String migrationFile) {
		this.config = config;
		this.oldConfig = FabricLoader.getInstance().getConfigDir().resolve(oldConfigLocation);
		this.newConfig = FabricLoader.getInstance().getConfigDir().resolve(newConfigLocation);
		this.migrationFile = FabricLoader.getInstance().getConfigDir().resolve(migrationFile);
	}

	public ConfigMigration<T> migrateInt(String oldKey, String newKey) {
		if (this.oldConfig.toFile().exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(this.oldConfig.toString()))) {
				JsonElement element = JsonParser.parseReader(jsonReader).getAsJsonObject().get(oldKey);

				if (element == null) {
					throw new SerializationException(
							String.format("The key of '%s' does not exist in %s", oldKey, this.oldConfig)
					);
				}

				int value = element.getAsInt();
				this.configValues.put(newKey, value);
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}

		return this;
	}

	/**
	 * Using this method means there is a 1:1 translation of the key from {@code config_a.json} to {@code config_b.json}.
	 */
	public ConfigMigration<T> migrateInt(String key) {
		this.migrateInt(key, key);

		return this;
	}

	public ConfigMigration<T> migrateBoolean(String oldKey, String newKey) {
		if (this.oldConfig.toFile().exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(this.oldConfig.toString()))) {
				JsonElement element = JsonParser.parseReader(jsonReader).getAsJsonObject().get(oldKey);

				if (element == null) {
					throw new SerializationException(
							String.format("The key of '%s' does not exist in %s", oldKey, this.oldConfig)
					);
				}

				boolean value = element.getAsBoolean();
				this.configValues.put(newKey, value);
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}

		return this;
	}

	/**
	 * Using this method means there is a 1:1 translation of the key from {@code config_a.json} to {@code config_b.json}.
	 */
	public ConfigMigration<T> migrateBoolean(String key) {
		this.migrateBoolean(key, key);

		return this;
	}

	public <E extends Enum<E>> ConfigMigration<T> migrateEnum(String oldKey, String newKey, EnumSpecification specification) {
		specification.validate();

		if (this.oldConfig.toFile().exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(this.oldConfig.toString()))) {
				JsonElement element = JsonParser.parseReader(jsonReader).getAsJsonObject().get(oldKey);

				if (element == null) {
					throw new SerializationException(
							String.format("The key of '%s' does not exist in %s", oldKey, this.oldConfig)
					);
				}

				String value = element.getAsString();
				EnumSpecification.Value<E> finalMapper = specification.convert(value);
				this.configEnumValues.put(newKey, finalMapper);
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}

		return this;
	}

	/**
	 * Using this method means there is a 1:1 translation of the key from {@code config_a.json} to {@code config_b.json}.
	 */
	public ConfigMigration<T> migrateEnum(String key, EnumSpecification specification) {
		this.migrateEnum(key, key, specification);

		return this;
	}

	public <E extends Enum<E>> ConfigMigration<T> migrateEnum(String oldKey, String newKey, EnumSpecification.Mapper mapper) {
		EnumSpecification specification = mapper.specification(new EnumSpecification());
		specification.validate();

		if (this.oldConfig.toFile().exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(this.oldConfig.toString()))) {
				JsonElement element = JsonParser.parseReader(jsonReader).getAsJsonObject().get(oldKey);

				if (element == null) {
					throw new SerializationException(
							String.format("The key of '%s' does not exist in %s", oldKey, this.oldConfig)
					);
				}

				String value = element.getAsString();
				EnumSpecification.Value<E> finalMapper = specification.convert(value);
				this.configEnumValues.put(newKey, finalMapper);
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}

		return this;
	}

	/**
	 * Using this method means there is a 1:1 translation of the key from {@code config_a.json} to {@code config_b.json}.
	 */
	public ConfigMigration<T> migrateEnum(String key, EnumSpecification.Mapper mapper) {
		this.migrateEnum(key, key, mapper);

		return this;
	}

	protected boolean migrate() {
		if (this.migrationFile.toFile().exists()) {
			try (JsonReader jsonReader = new JsonReader(new FileReader(this.migrationFile.toString()))) {
				this.hasMigrated = JsonParser.parseReader(jsonReader).getAsJsonObject().get("migrated").getAsBoolean();
			} catch (IOException e) {
				throw new SerializationException(e);
			}
		}

		if (!this.hasMigrated) {
			if (this.oldConfig.toFile().exists() && this.newConfig.toFile().exists()) {
				try (BufferedWriter fileWriter = Files.newBufferedWriter(this.newConfig)) {
					this.apply();
					this.gson.toJson(this.config, fileWriter);
				} catch (IOException e) {
					throw new SerializationException(e);
				}

				try (BufferedWriter fileWriter = Files.newBufferedWriter(this.migrationFile)) {
					this.gson.toJson(
							JsonParser.parseString(
									this.gson.toJson(
											Map.of("migrated", true)
									)
							),
							fileWriter
					);
				} catch (IOException e) {
					throw new SerializationException(e);
				}
			}

			return true;
		}

		return false;
	}

	/**
	 * Take the vales from {@link ConfigMigration#configValues} and {@link ConfigMigration#configEnumValues}
	 * and apply them to {@link ConfigMigration#config}.
	 */
	private void apply() {
		this.configValues.forEach((key, value) -> {
			try {
				Field field = this.config.getClass().getDeclaredField(key);
				field.setAccessible(true);

				if (!field.getType().isEnum()) { // enums need to handled differently, see next forEach
					field.set(this.config, value);
				}
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});

		this.configEnumValues.forEach((key, value) -> {
			try {
				Field field = this.config.getClass().getDeclaredField(key);
				field.setAccessible(true);
				field.set(this.config, Enum.valueOf(value.enumClass, value.value));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
