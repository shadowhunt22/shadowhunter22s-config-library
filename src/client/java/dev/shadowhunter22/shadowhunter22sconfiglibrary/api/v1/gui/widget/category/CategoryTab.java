//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import java.util.function.Consumer;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.screen.AbstractConfigScreen;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidgetHolder;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

public class CategoryTab implements Tab {
	private final ConfigCategory category;
	private final ConfigEntryWidgetHolder<ConfigEntryWidget> widgetHolder;
	protected final LinearLayout layout = LinearLayout.vertical();

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
	public Component getTabTitle() {
		return this.category.getCategoryName();
	}

	@Override
	public Component getTabExtraNarration() {
		return Component.empty();
	}

	@Override
	public void visitChildren(Consumer<AbstractWidget> consumer) {
		consumer.accept(this.widgetHolder);
	}

	@Override
	public void doLayout(ScreenRectangle tabArea) {
	}

	@Override
	public Layout getLayout() {
		return this.layout;
	}
}
