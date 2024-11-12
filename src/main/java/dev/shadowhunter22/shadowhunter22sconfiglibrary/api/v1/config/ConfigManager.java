//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import java.util.Map;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.serializer.ConfigSerializer;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.ConfigOption;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ConfigManager extends AbstractConfigManager {
    private final Map<String, ConfigOption<?>> config;
    private final String definition;
    private final ConfigSerializer serializer;

    public ConfigManager(Map<String, ConfigOption<?>> config, String definition) {
        this.config = config;
        this.definition = definition;
        this.serializer = new ConfigSerializer(definition, config);

        this.load();
    }

    public void save() {
        this.serializer.serialize(this.config);
    }

    @Override
    protected void load() {
        this.serializer.deserialize();
    }

    public Map<String, ConfigOption<?>> getConfig() {
        return this.config;
    }

    @Override
	public String getDefinition() {
        return this.definition;
    }

    @Override
    public ConfigSerializer getSerializer() {
        return this.serializer;
    }
}
