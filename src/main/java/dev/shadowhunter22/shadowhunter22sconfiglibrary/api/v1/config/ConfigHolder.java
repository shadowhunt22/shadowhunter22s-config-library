//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

/**
 * A holder that defines the type of functionality the {@link ConfigManager} has.
 */
public interface ConfigHolder<T extends ConfigData> {
    void save();
    boolean load();
    T getConfig();
}
