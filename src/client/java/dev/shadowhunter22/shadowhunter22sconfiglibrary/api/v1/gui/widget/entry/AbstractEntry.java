//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.SimpleLayoutWidget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public abstract class AbstractEntry implements GuiEventListener, Renderable, NarratableEntry {
	protected final Minecraft minecraft = Minecraft.getInstance();

	protected final AutoConfigManager<? extends ConfigData> manager;
	protected final String key;
	protected final int width;

	protected SimpleLayoutWidget layout = new SimpleLayoutWidget(this.minecraft.screen);

	protected boolean focused;
	protected boolean hovered;

	public <T extends ConfigData> AbstractEntry(AutoConfigManager<T> manager, String key, int width) {
		this.manager = manager;
		this.key = key;
		this.width = width;
	}

	public abstract ConfigEntryWidget.Entry build();

	protected abstract Component translatableText(String text);

	public SimpleLayoutWidget getLayoutWidget() {
		return this.layout;
	}

	public String getKey() {
		return this.key;
	}

	public void setY(int y) {
		this.layout.visitChildren(child -> child.setY(y));
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
	public NarrationPriority narrationPriority() {
		if (this.isFocused()) {
			return NarratableEntry.NarrationPriority.FOCUSED;
		} else {
			return this.hovered ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
		}
	}

	@Override
	public void updateNarration(NarrationElementOutput narrationElementOutput) {
	}
}
