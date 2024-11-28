//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.SimpleLayoutWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

public abstract class AbstractEntry implements Element, Selectable {
	protected final MinecraftClient client = MinecraftClient.getInstance();

	protected final AutoConfigManager<? extends ConfigData> manager;
	protected final String key;
	protected final int width;

	protected SimpleLayoutWidget layout = new SimpleLayoutWidget(this.client.currentScreen);
	protected boolean focused;
	protected boolean hovered;

	public <T extends ConfigData> AbstractEntry(AutoConfigManager<T> manager, String key, int width) {
		this.manager = manager;
		this.key = key;
		this.width = width;
	}

	public abstract ConfigEntryWidget.Entry build();
	protected abstract Text translatableText(String text);

	public SimpleLayoutWidget getLayout() {
		return this.layout;
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
