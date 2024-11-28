//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.category;

import java.util.function.Consumer;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidgetHolder;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class CategoryTab implements Tab {
	public final ConfigCategory category;
	private final ConfigEntryWidgetHolder<ConfigEntryWidget> widget;

	public CategoryTab(ConfigCategory category, Screen screen) {
		this.category = category;

		ConfigEntryWidget entryWidget = new ConfigEntryWidget(category.manager, MinecraftClient.getInstance(), screen.width, screen.height);

		for (AbstractEntry entry : this.category.entries) {
			entryWidget.add(entry);
		}

		this.widget = new ConfigEntryWidgetHolder<>(
				entryWidget
		);
	}

	@Override
	public Text getTitle() {
		return this.category.getText();
	}

	@Override
	public void forEachChild(Consumer<ClickableWidget> consumer) {
		consumer.accept(this.widget);
	}

	@Override
	public void refreshGrid(ScreenRect tabArea) {
	}
}
