//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SectionEntry<T extends ConfigData> extends AbstractEntry {
	private final ConfigManager<T> manager;
	private final String fieldName;

	public SectionEntry(ConfigManager<T> manager, String fieldName, int width) {
		this.manager = manager;
		this.fieldName = fieldName;
		this.width = width;
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		Text text = this.translatableText(String.format("text.%s.%s.@Section", this.manager.getDefinition().name(), this.fieldName));

		TextWidget textWidget = new TextWidget(this.width, 20, text, this.client.textRenderer);
		textWidget.alignLeft();
		textWidget.setX(textWidget.getX() + 15);

		this.layout.addBody(textWidget);

		return new ConfigEntryWidget.Entry(this);
	}

	@Override
	protected Text translatableText(String text) {
		return Text.translatable(text).formatted(Formatting.WHITE);
	}
}
