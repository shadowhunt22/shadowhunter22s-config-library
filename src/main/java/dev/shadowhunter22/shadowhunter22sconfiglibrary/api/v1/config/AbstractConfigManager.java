//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.serializer.AbstractSerializer;

public abstract class AbstractConfigManager {
	public abstract String getDefinition();
	public abstract AbstractSerializer getSerializer();
	public abstract void save();
	protected abstract void load();
}
