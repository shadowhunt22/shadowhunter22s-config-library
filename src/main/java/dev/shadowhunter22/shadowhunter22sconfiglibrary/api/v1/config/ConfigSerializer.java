//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

@ApiStatus.Internal
public class ConfigSerializer<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/ConfigSerializer");

    private final Class<T> configClass;
    private final Config definition;
    private final Gson gson;

    private ConfigSerializer(Config definition, Class<T> configClass, Gson gson) {
        this.configClass = configClass;
        this.gson = gson;
        this.definition = definition;
    }

    public ConfigSerializer(Config definition, Class<T> configClass) {
        this(definition, configClass, new GsonBuilder().setPrettyPrinting().create());
    }

    private Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(this.getConfigFileDirectory() + "/" + this.getConfigFileName());
    }

    private String getConfigFileDirectory() {
        return this.definition.name();
    }

    private String getConfigFileName() {
        return this.definition.file().isEmpty() ? "options.json" : this.definition.file() + ".json";
    }

    private void createDirectoryIfAbsent() {
        if (!Files.exists(this.getConfigPath())) {
            String configFileDirectory = this.getConfigFileDirectory();

            LOGGER.info("Unable to find a config file for {}/{}.  Creating...", configFileDirectory, this.getConfigFileName());

            File directory = new File(FabricLoader.getInstance().getConfigDir() + "/" + configFileDirectory);

            if (!directory.exists()) {
                boolean created = directory.mkdir();

                if (created) {
                    LOGGER.info("Successfully created a config directory for {}.", configFileDirectory);
                }
            }
        }
    }

    private void writeToConfigFile(T config) {
        try (BufferedWriter fileWriter = Files.newBufferedWriter(this.getConfigPath())) {
            this.gson.toJson(config, fileWriter);
        } catch (IOException e) {
            LOGGER.warn("Unable to serialize config file: {}", this.getConfigPath());
            throw new SerializationException(e);
        }
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
                LOGGER.warn("Unable to deserialize config file: {}", configPath);
                throw new SerializationException(e);
            }
        } else {
            T config = this.constructConfig();

            this.createDirectoryIfAbsent();
            this.writeToConfigFile(config);

            return config;
        }
    }

    public void setValue(Field field, T config, Object newValue) {
        try {
            field.setAccessible(true);
            field.set(config, newValue);
        } catch (ReflectiveOperationException e) {
            LOGGER.warn("Failed to set new value for config class: {}", config.getClass().getName());
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
}
