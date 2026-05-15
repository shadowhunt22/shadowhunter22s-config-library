//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import java.util.function.Consumer;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidgetHolder;

import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class CategoryTab implements Tab {
	private final ConfigCategory category;
	private final ConfigEntryWidgetHolder<ConfigEntryWidget> widgetHolder;

	public CategoryTab(ConfigCategory category) {
		this.category = category;
		this.widgetHolder = new ConfigEntryWidgetHolder<>(category.configEntryWidget);
	}

	/**
	 * Retrieve the {@link ConfigEntryWidget} from the {@link CategoryTab#category} the tab is associated with.
	 * This is usually needed when a mod fails to create two categories.
	 *
	 * @return {@link ConfigEntryWidget}
	 * @see AbstractConfigScreen#addTabWidget() AbstractConfigScreen#addTabWidget
	 */
	public ConfigEntryWidget getEntryWidget() {
		return this.category.configEntryWidget;
	}

	@Override
	public Text getTitle() {
		return this.category.getCategoryName();
	}

	@Override
	public Text getNarratedHint() {
		return Text.empty();
	}

	@Override
	public void forEachChild(Consumer<ClickableWidget> consumer) {
		consumer.accept(this.widgetHolder);
	}

	@Override
	public void refreshGrid(ScreenRect tabArea) {
	}
}
