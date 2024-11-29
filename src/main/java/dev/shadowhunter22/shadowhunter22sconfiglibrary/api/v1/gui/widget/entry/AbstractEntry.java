//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

public abstract class AbstractEntry implements Element, Drawable, Selectable {
	protected final MinecraftClient client = MinecraftClient.getInstance();

	protected final AutoConfigManager<? extends ConfigData> manager;
	protected final String key;
	protected final int width;
	protected int y = 0;

	protected ListWidget listWidget = new ListWidget();
	protected boolean focused;
	protected boolean hovered;

	public <T extends ConfigData> AbstractEntry(AutoConfigManager<T> manager, String key, int width) {
		this.manager = manager;
		this.key = key;
		this.width = width;
	}

	public abstract ConfigEntryWidget.Entry build();
	protected abstract Text translatableText(String text);

	public ListWidget getListWidget() {
		return this.listWidget;
	}

	public void setY(int y) {
		this.y = y;

		this.listWidget.children.forEach(child -> {
			child.setY(y);
		});
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public boolean isFocused() {
		return this.focused;
	}

	@Override
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	@Override
	public SelectionType getType() {
		if (this.isFocused()) {
			return Selectable.SelectionType.FOCUSED;
		} else {
			return this.hovered ? Selectable.SelectionType.HOVERED : Selectable.SelectionType.NONE;
		}
	}

	@Override
	public void appendNarrations(NarrationMessageBuilder builder) {
	}
}
