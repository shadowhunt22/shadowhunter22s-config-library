//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.annotation.Config;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.serializer.AutoConfigSerializer;

public class AutoConfigManager<T extends ConfigData> extends AbstractConfigManager {
    private final AutoConfigSerializer<T> serializer;

    private final Config definition;

    private T config;

    public AutoConfigManager(Class<T> configClass) {
        this.definition = configClass.getAnnotation(Config.class);
        this.serializer = new AutoConfigSerializer<>(this.definition, configClass);

        this.load();
    }

    @Override
    public void save() {
        this.serializer.serialize(this.config);
        this.config.afterSave();
    }

    @Override
    public void load() {
        this.config = this.serializer.deserialize();
    }

    public T getConfig() {
        return this.config;
    }

    @Override
    public AutoConfigSerializer<T> getSerializer() {
        return this.serializer;
    }

    @Override
    public String getDefinition() {
        return this.definition.name();
    }
}
