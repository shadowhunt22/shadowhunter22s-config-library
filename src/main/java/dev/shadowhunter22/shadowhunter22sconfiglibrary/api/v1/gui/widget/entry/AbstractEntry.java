//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import java.lang.reflect.Field;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.text.Text;

public abstract class AbstractEntry implements Element, Selectable {
	protected MinecraftClient client = MinecraftClient.getInstance();

	protected Field field;

	protected ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this.client.currentScreen);
	protected boolean focused;
	protected boolean hovered;

	protected int width;

	public abstract ConfigEntryWidget.Entry build();
	protected abstract Text translatableText(String text);

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
