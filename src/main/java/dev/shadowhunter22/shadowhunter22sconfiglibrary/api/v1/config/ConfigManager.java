//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import com.google.gson.JsonObject;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager<T extends ConfigData> implements ConfigHolder<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/ConfigManager");
    private final Class<T> configClass;

    private final Gson gson;
    private final Path path;

    public ConfigManager(Class<T> configClass) {
        this.configClass = configClass;

        this.path = this.prepareConfigFile();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    private Path prepareConfigFile() {
        String configFileName = this.configClass.getAnnotation(Config.class).name();

        File directory = new File(FabricLoader.getInstance().getConfigDir() + "/" + configFileName);

        if (!directory.exists()) {
            boolean created = directory.mkdir();

            if (created) {
                LOGGER.info("Successfully created a config directory for {}.", configFileName);
            }
        }

        return FabricLoader.getInstance().getConfigDir().resolve(configFileName + "/options.json");
    }

    @Override
    public void save() {
        if (this.path == null) {
            this.prepareConfigFile();
        }

        JsonObject config = new JsonObject();

        for (Field field : this.configClass.getDeclaredFields()) {
            if (!field.getClass().isArray()) {
                // TODO add different config option types and serialize them to JSON
            }
        }

        try (BufferedWriter fileWriter = Files.newBufferedWriter(this.path)) {
            fileWriter.write(config.toString());
        } catch (IOException e) {
            LOGGER.warn("Unable to save config file: {}", path);
            LOGGER.warn("{}", e.getMessage());
        }
    }

    @Override
    public boolean load() {
        if (this.path == null) {
            this.prepareConfigFile();
        }

        // TODO deserialize JSON to config options

        return false;
    }

    @Override
    public T getConfig() {
        if (this.path == null) {
            this.prepareConfigFile();
        }

        return null;
    }
}
