//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import java.util.function.Consumer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class ConfigEntryWidgetHolder<T extends ConfigEntryWidget> extends ClickableWidget {
	public final T list;

	public ConfigEntryWidgetHolder(T list) {
		super(0, 0, 100, 0, Text.empty());

		this.list = list;
	}

	@Override
	public void forEachChild(Consumer<ClickableWidget> consumer) {
		for (ConfigEntryWidget.Entry child : this.list.children()) {
			for (Element element : child.children()) {
				consumer.accept((ClickableWidget) element);
			}
		}
	}

	@Override
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		this.list.render(context, mouseX, mouseY, delta);
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}
}
