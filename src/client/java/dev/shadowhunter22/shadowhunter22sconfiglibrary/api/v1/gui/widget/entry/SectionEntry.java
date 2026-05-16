//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

public class SectionEntry extends AbstractEntry {
	private StringWidget textWidget;

	public <T extends ConfigData> SectionEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		Component text = this.translatableText(String.format("text.%s.%s.@Section", this.manager.getDefinition(), this.key));

		this.textWidget = new StringWidget(this.width, 20, text, this.minecraft.font);
		this.textWidget.setX(this.textWidget.getX() + 15);

		this.layout.addBody(this.textWidget);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	protected Component translatableText(String text) {
		return Component.translatable(text).withStyle(ChatFormatting.WHITE);
	}

	@Override
	public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
		this.textWidget.render(context, mouseX, mouseY, delta);
	}
}
