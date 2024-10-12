//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import java.lang.reflect.Field;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.option.BaseConfigOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;

public abstract class AbstractEntry<T extends ConfigData> implements Element, Selectable {
	protected MinecraftClient client = MinecraftClient.getInstance();

	protected ConfigManager<T> manager;

	protected Field field;
	protected BaseConfigOption<?> option;

	protected ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this.client.currentScreen);
	protected int width;
	protected boolean focused;
	protected boolean hovered;

	public AbstractEntry(ConfigManager<T> manager, Field field, BaseConfigOption<?> option, int width) {
		this.manager = manager;
		this.field = field;
		this.option = option;
		this.width = width;
	}

	public abstract ConfigEntryWidget.Entry build();

	public abstract void update();

	public ThreePartsLayoutWidget getLayout() {
		return this.layout;
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
