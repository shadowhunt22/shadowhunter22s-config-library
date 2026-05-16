//
// Copyright (c) 2026 by ShadowHunter22. All rights reserved.
// See LICENSE file in the project root for details.
//

package dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget;

import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.AutoConfigManager;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigData;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.config.ConfigRegistry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.AbstractEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.api.v1.gui.widget.entry.SectionEntry;
import dev.shadowhunter22.shadowhunter22sconfiglibrary.option.ConfigOption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class ConfigEntryWidget extends AbstractConfigEntryWidget<ConfigEntryWidget.Entry> {
	private final AutoConfigManager<?> manager;

	public <T extends ConfigData> ConfigEntryWidget(AutoConfigManager<T> manger, Minecraft minecraft, int width, int height) {
		super(minecraft, width, height);

		this.manager = manger;
	}

	public void add(AbstractEntry entry) {
		if (ConfigRegistry.hasSectionAnnotation(this.manager.getConfig().getClass(), entry.getKey())) {
			this.addSection(entry.getKey());
		}

		this.addEntry(entry.build());
	}

	public void add(String key, ConfigOption<?> option) {
		if (ConfigRegistry.hasSectionAnnotation(this.manager.getConfig().getClass(), key)) {
			this.addSection(key);
		}

		this.addEntry(
				option
						.asEntry(this.manager, this.width)
						.build()
		);
	}

	public void addSection(String key) {
		SectionEntry entry = new SectionEntry(this.manager, key, this.width);
		this.addEntry(entry.build());
	}

	public static class Entry extends AbstractConfigEntryWidget.Entry<Entry> {
		public Entry(AbstractEntry entry) {
			super(entry);
		}

		@Override
		public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
			this.entry.setY(this.getY());
			this.entry.extractRenderState(graphics, mouseX, mouseY, deltaTicks);
		}
	}
}
