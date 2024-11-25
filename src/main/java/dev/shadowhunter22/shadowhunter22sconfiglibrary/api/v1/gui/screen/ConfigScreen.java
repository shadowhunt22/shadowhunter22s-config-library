//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Colors;

import java.util.HashMap;

public class ConfigScreen<T extends ConfigData> extends AbstractConfigScreen {
    private final HashMap<String, ConfigOption<?>> options;

    ConfigEntryWidget configEntryWidget;

    protected ConfigScreen(AutoConfigManager<T> manager, HashMap<String, ConfigOption<?>> options, Screen parent) {
        super(manager, parent);

        this.options = options;
    }

    @Override
    protected void init() {
        this.configEntryWidget = new ConfigEntryWidget(this.manager, this.client, this.width, this.height, 50, this.height, 27);

        this.options.forEach((key, option) -> this.configEntryWidget.add(key, option));

        this.addDrawableChild(this.configEntryWidget);
    }
}
