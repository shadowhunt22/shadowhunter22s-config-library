//
// Copyright (c) 2024 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.ConfigEntryWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SectionEntry extends AbstractEntry {
	public <T extends ConfigData> SectionEntry(AutoConfigManager<T> manager, String key, int width) {
		super(manager, key, width);
	}

	@Override
	public ConfigEntryWidget.Entry build() {
		Text text = this.translatableText(String.format("text.%s.%s.@Section", this.manager.getDefinition(), this.key));

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
