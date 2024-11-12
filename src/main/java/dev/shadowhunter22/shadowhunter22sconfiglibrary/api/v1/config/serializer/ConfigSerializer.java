//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.serializer;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.ShadowHunter22sConfigLibraryClient;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.include.com.google.gson.Gson;
import org.spongepowered.include.com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@ApiStatus.Internal
public class ConfigSerializer extends AbstractSerializer {
    private final Map<String, ConfigOption<?>> options;
    private final String definition;
    private final Gson gson;

    private ConfigSerializer(String definition, Map<String, ConfigOption<?>> options, Gson gson) {
        this.options = options;
        this.gson = gson;
        this.definition = definition;
    }

    public ConfigSerializer(String configName, Map<String, ConfigOption<?>> options) {
        this(configName, options, new GsonBuilder().setPrettyPrinting().create());
    }

    protected Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(this.getConfigFileDirectory() + "/" + this.getConfigFileName());
    }

    protected String getConfigFileDirectory() {
        return this.definition;
    }

    protected String getConfigFileName() {
        return this.definition.isEmpty() ? "options.json" : this.definition + ".json";
    }

    protected void createDirectoryIfAbsent() {
        if (!Files.exists(this.getConfigPath())) {
            String configFileDirectory = this.getConfigFileDirectory();

            this.logger().info("Unable to find a config file for {}/{}.  Creating...", configFileDirectory, this.getConfigFileName());

            File directory = new File(FabricLoader.getInstance().getConfigDir() + "/" + configFileDirectory);

            if (!directory.exists()) {
                boolean created = directory.mkdir();

                if (created) {
                    this.logger().info("Successfully created a config directory for {}.", configFileDirectory);
                }
            }
        }
    }

    @Override
    protected Logger logger() {
        return LoggerFactory.getLogger(ShadowHunter22sConfigLibraryClient.MOD_ID + "/ConfigSerializer");
    }

    private void writeToConfigFile(Map<String, ConfigOption<?>> options) {
        try (BufferedWriter fileWriter = Files.newBufferedWriter(this.getConfigPath())) {
            Map<String, Object> entrySet = new LinkedHashMap<>();
            options.forEach((key, option) -> entrySet.put(key, option.getValue()));
            this.gson.toJson(entrySet, fileWriter);
        } catch (IOException e) {
            this.logger().warn("Unable to serialize config file: {}", this.getConfigPath());
            throw new SerializationException(e);
        }
    }

    public void serialize(Map<String, ConfigOption<?>> options) throws SerializationException {
        this.createDirectoryIfAbsent();
        this.writeToConfigFile(options);
    }

    public void deserialize() {
        // TODO implement with ConfigBuilder
    }

    public void setValue(String key, Object newValue) {
        this.options.get(key).setValue(newValue);
    }
}
