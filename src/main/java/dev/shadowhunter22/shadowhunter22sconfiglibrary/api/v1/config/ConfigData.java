//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

public interface ConfigData {
	default void afterLoad() {
	}

	default <T extends ConfigData> void afterChange(Class<T> config, String key) {
	}

	default void afterSave() {
	}
}
