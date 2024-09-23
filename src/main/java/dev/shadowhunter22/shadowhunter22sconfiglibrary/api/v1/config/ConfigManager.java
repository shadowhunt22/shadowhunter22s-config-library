//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ConfigManager<T extends ConfigData> implements ConfigHolder<T> {
    private final Class<T> configClass;
    private final ConfigSerializer<T> serializer;

    private T config;

    public ConfigManager(Class<T> configClass) {
        this.configClass = configClass;
        this.serializer = new ConfigSerializer<>(configClass.getAnnotation(Config.class), configClass);

        this.load();
    }

    @Override
    public void save() {
        this.serializer.serialize(this.config);
    }

    @Override
    public void load() {
        this.config = this.serializer.deserialize();
    }

    @Override
    public T getConfig() {
        return this.config;
    }
}
