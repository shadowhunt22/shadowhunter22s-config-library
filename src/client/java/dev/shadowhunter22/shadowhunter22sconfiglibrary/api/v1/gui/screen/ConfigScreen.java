//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import java.util.HashMap;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;

import net.minecraft.client.gui.screen.Screen;

public class ConfigScreen<T extends ConfigData> extends AbstractConfigScreen {
	private final HashMap<String, ConfigOption<?>> options;

	protected ConfigScreen(AutoConfigManager<T> manager, HashMap<String, ConfigOption<?>> options, Screen parent) {
		super(manager, parent);

		this.options = options;
	}

	@Override
	protected void init() {
		if (ConfigRegistry.numberOfCategoryAnnotations(this.manager.getConfig().getClass()) > 1) {
			this.initializeCategoryWidget();
		} else {
			ConfigEntryWidget configEntryWidget = new ConfigEntryWidget(this.manager, this.client, this.width, this.height);
			this.options.forEach(configEntryWidget::add);

			this.addDrawableChild(configEntryWidget);
		}
	}

	private void initializeCategoryWidget() {
		this.options.forEach((key, option) -> {
			this.addToOrCreateCategory(
					option.asEntry(this.manager, this.width),
					ConfigRegistry.hasCategoryAnnotation(this.manager.getConfig().getClass(), key)
			);
		});

		this.addTabWidget();
	}
}
