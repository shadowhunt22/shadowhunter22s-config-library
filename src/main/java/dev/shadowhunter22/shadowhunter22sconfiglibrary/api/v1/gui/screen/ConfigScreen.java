//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category.ConfigCategory;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.TabNavigationWidget;

public class ConfigScreen<T extends ConfigData> extends AbstractConfigScreen {
    private final HashMap<String, ConfigOption<?>> options;

    ConfigEntryWidget configEntryWidget;

    protected ConfigScreen(AutoConfigManager<T> manager, HashMap<String, ConfigOption<?>> options, Screen parent) {
        super(manager, parent);

        this.options = options;
    }

    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);

	@Override
    protected void init() {
        this.configEntryWidget = new ConfigEntryWidget(this.manager, this.client, this.width, this.height);
        this.options.forEach((key, option) -> this.configEntryWidget.add(key, option));

        if (this.configEntryWidget.hasMinimumRequiredCategories()) {
            // TODO ConfigEntryWidget and children elements not clickable
            this.initializeCategoryWidget();
        } else {
            this.addDrawableChild(this.configEntryWidget);
        }
    }

    private void initializeCategoryWidget() {
        List<Tab> tabs = new ArrayList<>();

        for (ConfigCategory category : this.configEntryWidget.categories) {
            tabs.add(category.getTab());
        }

        Tab[] tabsArray = tabs.toArray(new Tab[0]);

		TabNavigationWidget categoryWidget = TabNavigationWidget.builder(this.tabManager, this.width)
				.tabs(tabsArray)
				.build();

        this.addDrawableChild(categoryWidget);

        categoryWidget.selectTab(0, false);
        categoryWidget.init();
    }
}
