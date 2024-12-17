//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;

import net.minecraft.client.gui.widget.SliderWidget;

public abstract class AbstractSliderEntry extends AbstractOptionEntry {
	public <T extends ConfigData> AbstractSliderEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);
	}

	protected abstract SliderWidget createSliderWidget();
}
